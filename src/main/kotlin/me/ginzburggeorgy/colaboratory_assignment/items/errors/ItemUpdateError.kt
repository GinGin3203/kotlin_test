package me.ginzburggeorgy.colaboratory_assignment.items.errors

import kotlinx.serialization.Serializable

class ItemUpdateError(override val message: String?) : Error()