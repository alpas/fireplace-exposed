package dev.alpas.fireplace.controllers

import dev.alpas.fireplace.entities.Project
import dev.alpas.fireplace.entities.Projects
import dev.alpas.fireplace.entities.User
import dev.alpas.http.HttpCall
import dev.alpas.orAbort
import dev.alpas.routing.Controller
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

class ProjectController : Controller() {
    fun index(call: HttpCall) {
        transaction {
            val projects = caller<User>().ownedProjects.toList()
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

    fun delete(call: HttpCall) {
        val success = transaction {
            val id = call.paramAsLong("id").orAbort()
            val caller = caller<User>()
            Projects.deleteWhere {
                (Projects.id eq id) and (Projects.owner eq caller.id)
            } > 0
        }
        if (success) {
            flash("success", "Successfully deleted the project!")
        } else {
            flash("error", "There was an error deleting the project. Please try again.")
        }
        call.redirect().back()
    }
}
