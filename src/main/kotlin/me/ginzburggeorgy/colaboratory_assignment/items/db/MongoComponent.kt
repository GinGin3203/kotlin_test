package me.ginzburggeorgy.colaboratory_assignment.items.db

import com.mongodb.client.MongoClient
import org.litote.kmongo.KMongo
import org.springframework.stereotype.Component

@Component
class MongoComponent {
    companion object {
        const val CONNECTION_STRING = "mongodb://localhost:27017"
        const val DATABASE_NAME = "colaboratory"
    }

    val client: MongoClient = KMongo.createClient(CONNECTION_STRING)
}