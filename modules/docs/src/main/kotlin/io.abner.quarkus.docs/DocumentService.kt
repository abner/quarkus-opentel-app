package io.abner.quarkus.docs

import io.abner.quarkus.opentel.infra.TraceSpan
import io.opentelemetry.api.trace.Tracer
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
@Default
class DocumentService {

    @Inject
    lateinit var tracer: Tracer

    @TraceSpan()
    suspend fun get(id: String): Map<String, Any>  {
        print("THREAD INSTANCE (DocumentService.get): ${Thread.currentThread().id}")
        val doc: Map<String, Any> =  mapOf(
            "id" to id,
            "name" to "Document: $id"
        )
        return doc
    }

    suspend fun get2(id: String): Map<String, Any> {
        val doc: Map<String, Any> =  mapOf(
            "id" to id,
            "name" to "Document: $id"
        )

        return doc
    }

    fun getSync(id: String): Any {
        val doc: Map<String, Any> =  mapOf(
            "id" to id,
            "name" to "Document: $id"
        )

        return doc
    }
}