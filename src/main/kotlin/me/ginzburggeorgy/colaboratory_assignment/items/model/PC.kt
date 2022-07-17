package me.ginzburggeorgy.colaboratory_assignment.items.model

import kotlinx.serialization.Serializable

@Serializable
class PC(
    override var _id: String?,
    override val name: String,
    override val priceDollars: Int,
    override val count: Int,
    val graphicsCardModel: String
) : Item