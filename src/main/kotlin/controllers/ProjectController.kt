package dev.alpas.fireplace.controllers

import dev.alpas.fireplace.entities.Project
import dev.alpas.fireplace.entities.Projects
import dev.alpas.fireplace.entities.User
import dev.alpas.fireplace.guards.CreateProjectGuard
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
            call.validateUsing(CreateProjectGuard::class) {
                val project = commit()
                flash("success", "Successfully added project '${project.title}'!")
            }
        }
        call.redirect().toRouteNamed("projects.list")
    }

    fun show(call: HttpCall) {
        val projectId = call.paramAsLong("id").orAbort()
        val userId = auth().user?.id()
        transaction {
            val project = Project.find {
                (Projects.owner eq userId) and (Projects.id eq projectId)
            }.firstOrNull().orAbort()

            call.render("project_show", mapOf("project" to project))
        }
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
