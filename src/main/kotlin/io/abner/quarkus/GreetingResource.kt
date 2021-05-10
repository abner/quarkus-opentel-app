package io.abner.quarkus

import io.abner.quarkus.coroutines.QuarkusCoroutineScope
import io.quarkus.vertx.http.runtime.CurrentVertxRequest
import io.smallrye.mutiny.Uni
import io.abner.quarkus.services.CustomService
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello-resteasy")
class GreetingResource @Inject constructor(
    private val scope: QuarkusCoroutineScope
) {

    @Inject
    lateinit var customService: CustomService

    @Inject
    lateinit var vertxRequest: CurrentVertxRequest

    @GET
    @Path("/ready")
    fun ready(): Uni<String> = scope.asyncUni(spanName = "ready") {
        val span = io.opentelemetry.api.trace.Span.current()
        span.addEvent("BLA BLA BLA - on READY")
        customService.doSomething2()
        "OK"
    }

    @GET
    @Path("/readySync")
    fun readySync(): String {
        customService.doSomething2Sync()
        return "OK"
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun hello(): Uni<String> = scope.asyncUni(spanName = "Greeting-MyScope.hello") {
        println("THREAD INSTANCE (GreetingResource.hello): ${Thread.currentThread().id}")

        if (vertxRequest.current != null) {
            println("INJECTED REQUEST")
            if (vertxRequest.current.data() != null) {
                println("REQUEST DATA: ${vertxRequest.current.data()}")
            }
        } else {
            println("MISSING INJECTED REQUEST")
        }

        customService.doSomething()
        "Hello RESTEasy"
    }

}