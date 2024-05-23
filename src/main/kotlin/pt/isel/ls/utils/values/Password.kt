package pt.isel.ls.utils.values

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Password(val value: String) {
    init {
        require(strongPasswordRegex.matches(value)) {
            "Invalid password format. Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character from the set !@#\$%^&*."
        }
    }

    companion object {
        private const val MIN_PASSWORD_SIZE = 8
        val strongPasswordRegex =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*])[A-Za-z\\d!@#\$%^&*]{$MIN_PASSWORD_SIZE,}$")
    }
}
