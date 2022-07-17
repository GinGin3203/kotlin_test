package me.ginzburggeorgy.colaboratory_assignment.orders.rest

import me.ginzburggeorgy.colaboratory_assignment.orders.model.OrderWriteModel
import me.ginzburggeorgy.colaboratory_assignment.orders.service.OrdersService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

interface OrdersHandler {
    suspend fun createNewOrder(request: ServerRequest): ServerResponse
    suspend fun getItemIdsByOrderCount(request: ServerRequest): ServerResponse
}

@Component
class OrdersHandlerImpl(
    val ordersService: OrdersService,
) : OrdersHandler {
    override suspend fun createNewOrder(request: ServerRequest): ServerResponse {
        val order = request.awaitBody<OrderWriteModel>()
        runCatching {
            ordersService.createNewOrder(order)
        }.onFailure {
            return ServerResponse
                .badRequest()
                .bodyValueAndAwait(Pair("message", it.message))
        }

        return ServerResponse
            .ok()
            .bodyValueAndAwait(Pair("message", "ok"))
    }

    override suspend fun getItemIdsByOrderCount(request: ServerRequest): ServerResponse {
        return ServerResponse
            .ok()
            .bodyAndAwait(ordersService.getItemIdsByOrderCount())
    }
}