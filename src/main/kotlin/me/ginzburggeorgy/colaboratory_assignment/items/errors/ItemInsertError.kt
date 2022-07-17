package me.ginzburggeorgy.colaboratory_assignment.items.errors

import kotlinx.serialization.Serializable

class ItemInsertError(override val message: String?) : Error()
