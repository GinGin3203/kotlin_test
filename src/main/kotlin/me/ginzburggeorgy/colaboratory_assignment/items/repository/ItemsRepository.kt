package me.ginzburggeorgy.colaboratory_assignment.items.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import me.ginzburggeorgy.colaboratory_assignment.items.db.MongoComponent
import me.ginzburggeorgy.colaboratory_assignment.items.model.Item
import org.litote.kmongo.*
import org.springframework.stereotype.Repository


interface ItemsRepository {
    fun insertNewItem(item: Item): Item?

    // Returns true if decrement was successful; false otherwise
    fun tryDecrementItemCount(itemId: String): Boolean
    fun getAllItems(): Flow<Item>
}

@Repository
class ItemsRepositoryImpl(mongoComponent: MongoComponent) : ItemsRepository {
    private val client = mongoComponent.client
    private val database = client.getDatabase(MongoComponent.DATABASE_NAME)
    private val collection = database.getCollection<Item>()

    // It would be nice to split this into getting an item, checking if it exists and if the count is not 0
    // and only then doing something on the service layer, but it would require dealing with transactions,
    // so just having this function is kinda easier
    override fun tryDecrementItemCount(itemId: String): Boolean {
        val updateRes = collection.updateOne(
            and(Item::_id eq itemId, Item::count gt 0),
            inc(Item::count, -1)
        )
        return updateRes.matchedCount == 1L
    }

    override fun getAllItems(): Flow<Item> = collection
        .find()
        .asFlow()

    override fun insertNewItem(item: Item): Item? {
        val insertRes = collection.insertOne(item)
        if (!insertRes.wasAcknowledged()) {
            return null
        } else {
            item._id = insertRes.insertedId.toString()
        }
        return item
    }
}