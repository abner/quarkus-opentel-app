package io.abner.quarkus.services

import io.abner.quarkus.docs.DocumentService
import io.abner.quarkus.opentel.infra.TraceSpan
import io.opentelemetry.api.trace.Tracer
import org.eclipse.microprofile.context.ManagedExecutor
import org.eclipse.microprofile.context.ThreadContext
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class CustomService {

    @Inject
    lateinit var tracer: Tracer

    @Inject
    @field: Default
    lateinit var documentService: DocumentService

    @Inject
    lateinit var threadContext: ThreadContext

    @Inject
    lateinit var managedExecutor: ManagedExecutor

    //@io.abner.quarkus.docs.interceptors.WithSpan
    @TraceSpan
    suspend fun doSomething() {
        val document = documentService.get("1")

        println("Document: ${document.toString()}")

        //span.end()
    }


    suspend fun doSomething2() {
        val document = documentService.get2("1")
        println("Document: ${document.toString()}")
    }

    fun doSomething2Sync() {
        val document = documentService.getSync("1")
        println("DOCUMENT: $document")
    }

}

