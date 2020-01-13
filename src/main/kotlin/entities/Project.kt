package dev.alpas.fireplace.entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.`java-time`.timestamp

object Projects : LongIdTable("projects") {
    val title = text("title")
    val description = text("description")
    val notes = text("notes").nullable()
    val owner = reference("owner_id", Users, onDelete = ReferenceOption.CASCADE)
    val createdAt = timestamp("created_at")
    val updatedAt = timestamp("updated_at")
}

class Project(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Project>(Projects)
    var title by Projects.title
    var description by Projects.description
    var notes by Projects.notes
    var owner by User.referencedOn(Projects.owner)
    var createdAt by Projects.createdAt
    var updatedAt by Projects.updatedAt
}

