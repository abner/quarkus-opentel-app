//package org.acme.rective.interceptors
//
//import io.opentelemetry.api.trace.Span
//import io.opentelemetry.api.trace.Tracer
//import io.opentelemetry.context.Context
//import io.vertx.core.Vertx
//import org.acme.QuarkusCoroutineScope
//import javax.annotation.Priority
//import javax.inject.Inject
//import javax.inject.Singleton
//import javax.interceptor.AroundInvoke
//import javax.interceptor.Interceptor
//import javax.interceptor.InvocationContext
//
//
//@WithSpan("", "", false)
//@Priority(10)
//@Singleton
//@Interceptor
//class WithSpanInterceptor {
//
////    @Inject
////    lateinit var threadContext: ThreadContext
////
////    @Inject
////    lateinit var managedExecutor: ManagedExecutor
//
//    @Inject
//    lateinit var tracer: Tracer
//
//    @AroundInvoke
//    @Throws(Exception::class)
//    fun span(ctx: InvocationContext): Any {
//        var methodName = ctx.method.name
//        val vertxContext = checkNotNull(Vertx.currentContext())
//        val parentSpan = vertxContext.get<Span>(QuarkusCoroutineScope.CONTEXT_NAME)
//        var spanBuilder = tracer.spanBuilder(methodName)
//        if (parentSpan != null) {
//            spanBuilder.setParent(Context.current().with(parentSpan))
//        }
//        val span = spanBuilder.startSpan()
//        var res = ctx.proceed()
//        span.end()
//        return res
//    }
////    @AroundInvoke
////    @Throws(Exception::class)
////    fun log(ctx: InvocationContext): Any {
////
////        var ret: Any? = null
////        threadContext.withContextCapture(generateSpanOnContext(ctx))
////            .thenApplyAsync({ result ->
////                print("Algo assincrono")
////                ret = result
////            }, managedExecutor).toCompletableFuture().join()
////
////        return ret!!
////    }
//
//
//
////    companion object {
////        val LOG = AtomicReference<Any>()
////    }
////
////    fun generateSpanOnContext(ctx: InvocationContext): CompletionStage<Any> {
////        var methodName = ctx.method.name
////        val vertxContext = checkNotNull(Vertx.currentContext())
////        val parentSpan = vertxContext.get<Span>(MyScope.CONTEXT_NAME)
////        var spanBuilder = tracer.spanBuilder(methodName)
////
////        if (parentSpan != null) {
////            spanBuilder.setParent(Context.current().with(parentSpan))
////        }
////        val span = spanBuilder  .startSpan()
////
////        var res = ctx.proceed()
////        val future: CompletableFuture<Any> = CompletableFuture<Any>()
////        future.complete(res)
////        span.end()
////        return future
////    }
//}