package me.ginzburggeorgy.colaboratory_assignment.items.model

interface Item {
    var _id: String?
    val name: String
    val priceDollars: Int
    val count: Int
}

