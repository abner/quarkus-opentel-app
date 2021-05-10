package io.abner.quarkus.opentel.infra

import io.abner.quarkus.coroutines.QuarkusCoroutineScope
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.Tracer
import io.opentelemetry.context.Context
import io.vertx.core.Vertx
import javax.annotation.Priority
import javax.inject.Inject
import javax.inject.Singleton
import javax.interceptor.AroundInvoke
import javax.interceptor.Interceptor
import javax.interceptor.InvocationContext

@TraceSpan()
@Priority(10)
@Singleton
@Interceptor
class TraceSpanInterceptor {
    @Inject
    lateinit var tracer: Tracer

    @AroundInvoke
    @Throws(Exception::class)
    fun span(ctx: InvocationContext): Any {
        var methodName = ctx.method.name
        val vertxContext = checkNotNull(Vertx.currentContext())
        //val parentSpan = vertxContext.get<Span>(QuarkusCoroutineScope.CONTEXT_NAME)
        val parentSpan = Span.current()
        var spanBuilder = tracer.spanBuilder(methodName)
        if (parentSpan != null) {
            spanBuilder.setParent(Context.current().with(parentSpan))
        }
        val span = spanBuilder.startSpan()
        var res = ctx.proceed()
        span.end()
        return res
    }
}