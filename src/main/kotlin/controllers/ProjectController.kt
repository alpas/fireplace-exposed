package dev.alpas.fireplace.controllers

import dev.alpas.fireplace.entities.Project
import dev.alpas.http.HttpCall
import dev.alpas.routing.Controller
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectController : Controller() {
    fun index(call: HttpCall) {
        transaction {
            val projects = Project.all().toList()
            call.render("project_list", mapOf("projects" to projects))
        }
    }

    fun create(call: HttpCall) {
        call.render("project_new")
    }

    fun store(call: HttpCall) {
        transaction {
            val now = call.nowInCurrentTimezone().toInstant()
            val titleParam = call.paramAsString("title")!!
            Project.new {
                title = titleParam
                description = call.paramAsString("description")!!
                owner = caller()
                createdAt = now
                updatedAt = now
            }
            flash("success", "Successfully added project '${titleParam}'!")
        }
        call.redirect().toRouteNamed("projects.list")
    }
}
