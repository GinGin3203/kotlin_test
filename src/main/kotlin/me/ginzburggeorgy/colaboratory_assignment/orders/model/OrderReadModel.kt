package me.ginzburggeorgy.colaboratory_assignment.orders.model

import java.time.LocalDateTime

data class OrderReadModel(
    var id: Int = 0,
    var itemId: String,
    val createdAt: LocalDateTime
) {


}
