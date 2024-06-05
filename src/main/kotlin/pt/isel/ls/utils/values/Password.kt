package pt.isel.ls.utils.values

import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@JvmInline
@Serializable
value class Password(val value: String) {
    init {
        require(strongPasswordRegex.matches(value)) {
            "Invalid password format. Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character from the set !@#\$%^&*."
        }
    }

    /**
     * Generates a bcrypt hash of the input value using a generated salt.
     *
     * This function uses the `BCrypt.hashpw` method to create a bcrypt hash of the input value.
     * The `BCrypt.gensalt` method is used to generate a random salt with the default log rounds (strength).
     *
     * @return A bcrypt hashed version of the input value as a `String`.
     *
     * Example usage:
     * ```
     * val password = "mySecurePassword"
     * val hashedPassword = hash(password)
     * println(hashedPassword)  // Prints the bcrypt hash of the password
     * ```
     *
     * Note: The input value is expected to be a variable named `value` in the surrounding context.
     * Ensure that the value is securely handled and not exposed inappropriately.
     */
    fun hash(): String = BCrypt.hashpw(value, BCrypt.gensalt())

    fun verify(hashed: String): Boolean = BCrypt.checkpw(value, hashed)

    companion object {
        private const val MIN_PASSWORD_SIZE = 8
        val strongPasswordRegex =
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*])[A-Za-z\\d!@#\$%^&*]{$MIN_PASSWORD_SIZE,}$")
    }
}
