package io.abner.quarkus.coroutines

import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import io.quarkus.vertx.http.runtime.CurrentVertxRequest
import io.smallrye.mutiny.Uni
import io.smallrye.mutiny.coroutines.asUni
import io.vertx.core.Vertx
import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.*
import javax.enterprise.context.RequestScoped
import javax.enterprise.inject.spi.CDI
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


@RequestScoped
class QuarkusCoroutineScope : CoroutineScope {

    companion object {
        val CONTEXT_NAME = "CoroutineParentSpan"
    }

    override val coroutineContext: CoroutineContext = SupervisorJob()

    @Inject // not working on Quarkus 1 and RestEasy-Reactive
    lateinit var routingContext: RoutingContext

    @Inject
    lateinit var tracer: Tracer

    @Inject
    private lateinit var currentVertxRequest: CurrentVertxRequest

    @ExperimentalCoroutinesApi
    fun <T> asyncUni(
        spanName: String = "<<spanNameNotSet>>",
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> T
    ): Uni<T> {
        val vertxContext = checkNotNull(Vertx.currentContext())
        val dispatcher = VertxCoroutineExecutor(vertxContext).asCoroutineDispatcher()
        val parentSpan = tracer.spanBuilder(spanName)
            .setParent(Context.current().with(Span.current()))
            .startSpan()
        vertxContext.put(CONTEXT_NAME, parentSpan)

        return async(context + dispatcher, start, block).asUni()
            .onItem()
            .invoke { _ ->
                parentSpan.end()
            }
            .onFailure()
            .recoverWithUni { e ->
                parentSpan.recordException(e)
                parentSpan.end()
                vertxContext.remove(CONTEXT_NAME)
                Uni.createFrom().failure(e)
            }
    }


    val vertxRequest
        get(): CurrentVertxRequest? {
            if (currentVertxRequest == null) {
                currentVertxRequest = CDI.current()
                    .select(CurrentVertxRequest::class.java).get()
            }
            return currentVertxRequest
        }
}