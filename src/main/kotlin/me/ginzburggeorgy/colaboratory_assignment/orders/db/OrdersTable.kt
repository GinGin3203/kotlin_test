package me.ginzburggeorgy.colaboratory_assignment.orders.db

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime.now

object OrdersTable : IntIdTable("orders_table") {
    val itemId = varchar("item_id", 24)
    val createdAt = datetime("created_at").clientDefault { now() }
}

class OrdersDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<OrdersDAO>(OrdersTable)

    var itemId by OrdersTable.itemId
    var createdAt by OrdersTable.createdAt
}

