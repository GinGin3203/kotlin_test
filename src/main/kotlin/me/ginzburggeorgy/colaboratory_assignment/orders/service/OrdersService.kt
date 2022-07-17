package me.ginzburggeorgy.colaboratory_assignment.orders.service

import kotlinx.coroutines.flow.Flow
import me.ginzburggeorgy.colaboratory_assignment.items.errors.ItemUpdateError
import me.ginzburggeorgy.colaboratory_assignment.items.service.ItemsService
import me.ginzburggeorgy.colaboratory_assignment.orders.errors.OrderInsertError
import me.ginzburggeorgy.colaboratory_assignment.orders.model.ItemIdToOrderCount
import me.ginzburggeorgy.colaboratory_assignment.orders.model.OrderReadModel
import me.ginzburggeorgy.colaboratory_assignment.orders.model.OrderWriteModel
import me.ginzburggeorgy.colaboratory_assignment.orders.repository.OrdersRepository
import org.springframework.stereotype.Service

interface OrdersService {
    fun createNewOrder(order: OrderWriteModel): OrderReadModel?
    fun getItemIdsByOrderCount(): Flow<ItemIdToOrderCount>
}

@Service
class OrdersServiceImpl(
    val ordersRepository: OrdersRepository,
    val itemsService: ItemsService,
) : OrdersService {
    override fun createNewOrder(order: OrderWriteModel): OrderReadModel? {
        try {
            itemsService.tryDecrementItemCount(order.itemId)
        } catch (err: ItemUpdateError) {
            throw OrderInsertError("Could not create new order; reason: ${err.message}")
        }
        return ordersRepository.insertNewOrder(order)
    }

    override fun getItemIdsByOrderCount(): Flow<ItemIdToOrderCount> =
        ordersRepository.groupItemIdsByOrderCount()
}