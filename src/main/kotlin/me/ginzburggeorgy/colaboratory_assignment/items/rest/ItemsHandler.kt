package me.ginzburggeorgy.colaboratory_assignment.items.rest

import me.ginzburggeorgy.colaboratory_assignment.items.model.Item
import me.ginzburggeorgy.colaboratory_assignment.items.model.Laptop
import me.ginzburggeorgy.colaboratory_assignment.items.model.PC
import me.ginzburggeorgy.colaboratory_assignment.items.service.ItemsService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

interface ItemsHandler {
    suspend fun getAllItems(request: ServerRequest): ServerResponse
    suspend fun createNewItem(request: ServerRequest): ServerResponse
}

@Component
class ItemsHandlerImpl(val itemsService: ItemsService) : ItemsHandler {
    override suspend fun getAllItems(request: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyAndAwait(itemsService.getAllItems())

    override suspend fun createNewItem(request: ServerRequest): ServerResponse {
        val item = request.awaitBody<Item>()
        return ServerResponse
            .ok()
            .bodyValueAndAwait(itemsService.createNewItem(item))
    }
}
