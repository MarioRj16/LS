package pt.isel.ls.utils

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Email(val email: String) {
    init {
        require(validEmail.matches(email)) { "Invalid email format" }
    }

    companion object {
        val validEmail = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    }
}
