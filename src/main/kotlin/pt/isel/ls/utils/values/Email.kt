package pt.isel.ls.utils.values

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Email(val value: String) {
    init {
        require(validEmailRegex.matches(value)) {
            "Invalid email format: '$value'. Please provide a valid email address."
        }
    }

    companion object {
        val validEmailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    }
}
