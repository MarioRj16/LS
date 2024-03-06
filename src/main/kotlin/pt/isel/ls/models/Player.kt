package pt.isel.ls.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Player(
    val ID: Int,
    val name: String,
    val email: String,
    val token: String = "${UUID.randomUUID()}"
){
    init {
        require('@' !in email){"$email has to include an @"}
    }
}