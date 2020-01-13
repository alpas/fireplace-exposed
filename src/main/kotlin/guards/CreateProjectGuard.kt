package dev.alpas.fireplace.guards

import dev.alpas.validation.Rule
import dev.alpas.validation.ValidationGuard
import dev.alpas.validation.min
import dev.alpas.validation.required

class CreateProjectGuard : ValidationGuard() {
    override fun rules(): Map<String, Iterable<Rule>> {
        return mapOf("title" to listOf(required(), min(8)))
    }
}
