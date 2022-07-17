package me.ginzburggeorgy.colaboratory_assignment.orders.db

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils

object PostgresConfig {
    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/colaboratory",
            driver = "org.postgresql.Driver",
            user = "user",
            password = "password",
        )
        SchemaUtils.create(OrdersTable)
    }
}