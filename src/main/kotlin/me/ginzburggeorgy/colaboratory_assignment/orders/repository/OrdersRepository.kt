package me.ginzburggeorgy.colaboratory_assignment.orders.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import me.ginzburggeorgy.colaboratory_assignment.orders.db.OrdersDAO
import me.ginzburggeorgy.colaboratory_assignment.orders.db.OrdersTable
import me.ginzburggeorgy.colaboratory_assignment.orders.model.ItemIdToOrderCount
import me.ginzburggeorgy.colaboratory_assignment.orders.model.OrderReadModel
import me.ginzburggeorgy.colaboratory_assignment.orders.model.OrderWriteModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface OrdersRepository {
    fun insertNewOrder(order: OrderWriteModel): OrderReadModel
    fun groupItemIdsByOrderCount(): Flow<ItemIdToOrderCount>
}

fun OrdersDAO.toReadModel(): OrderReadModel = OrderReadModel(id.value, itemId, createdAt)

@Repository
class OrdersRepositoryImpl : OrdersRepository {
    override fun insertNewOrder(order: OrderWriteModel): OrderReadModel = transaction {
        OrdersDAO
            .new {
                itemId = order.itemId
            }
    }.toReadModel()

    override fun groupItemIdsByOrderCount(): Flow<ItemIdToOrderCount> = transaction {
        val ordersPerItem = OrdersTable.itemId.count()
        OrdersTable
            .slice(OrdersTable.itemId, ordersPerItem)
            .selectAll()
            .groupBy(OrdersTable.itemId)
            .map {
                ItemIdToOrderCount(
                    it[OrdersTable.itemId],
                    it[ordersPerItem]
                )
            }.asFlow()
    }
}