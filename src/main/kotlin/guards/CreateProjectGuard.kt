package dev.alpas.fireplace.guards

import dev.alpas.fireplace.entities.Project
import dev.alpas.orAbort
import dev.alpas.validation.Rule
import dev.alpas.validation.ValidationGuard
import dev.alpas.validation.min
import dev.alpas.validation.required

class CreateProjectGuard : ValidationGuard() {
    override fun rules(): Map<String, Iterable<Rule>> {
        return mapOf("title" to listOf(required(), min(8)))
    }

    fun commit(): Project {
        call.validateUsing(CreateProjectGuard::class)
        val titleParam = call.paramAsString("title").orAbort()
        val descParam = call.paramAsString("description").orAbort()
        val now = call.nowInCurrentTimezone().toInstant()

        return Project.new {
            title = titleParam
            description = descParam
            owner = call.caller()
            createdAt = now
            updatedAt = now
        }
    }
}
