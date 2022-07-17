package me.ginzburggeorgy.colaboratory_assignment.items.service

import kotlinx.coroutines.flow.Flow
import me.ginzburggeorgy.colaboratory_assignment.items.errors.ItemInsertError
import me.ginzburggeorgy.colaboratory_assignment.items.errors.ItemUpdateError
import me.ginzburggeorgy.colaboratory_assignment.items.model.Item
import me.ginzburggeorgy.colaboratory_assignment.items.repository.ItemsRepository
import org.springframework.stereotype.Service


interface ItemsService {
    fun createNewItem(item: Item): Item
    fun tryDecrementItemCount(itemId: String)
    fun getAllItems(): Flow<Item>
}

@Service
class ItemsServiceImpl(val itemsRepository: ItemsRepository) : ItemsService {
    override fun createNewItem(item: Item): Item {
        val insertedItem = itemsRepository.insertNewItem(item)
        if (insertedItem == null) {
            throw ItemInsertError("Could not insert item $item")
        } else {
            return insertedItem
        }
    }

    override fun tryDecrementItemCount(itemId: String) {
        if (itemsRepository.tryDecrementItemCount(itemId))
            throw ItemUpdateError("Item Id does not exist or item's count is 0")
    }

    override fun getAllItems(): Flow<Item> = itemsRepository.getAllItems()
}
