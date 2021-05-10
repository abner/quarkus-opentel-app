package io.abner.quarkus.coroutines

import io.vertx.core.Context
import io.vertx.core.Vertx
import kotlinx.coroutines.*
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.TimeUnit

class VertxCoroutineExecutor(
    private val vertxContext: Context
) : AbstractExecutorService() {

    override fun execute(command: Runnable) {
        if (Vertx.currentContext() != vertxContext) {
            vertxContext.runOnContext { command.run() }
        } else {
            command.run()
        }
    }

    override fun shutdown(): Unit = throw UnsupportedOperationException()
    override fun shutdownNow(): MutableList<Runnable> = throw UnsupportedOperationException()
    override fun isShutdown(): Boolean = throw UnsupportedOperationException()
    override fun isTerminated(): Boolean = throw UnsupportedOperationException()
    override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean = throw UnsupportedOperationException()
}

