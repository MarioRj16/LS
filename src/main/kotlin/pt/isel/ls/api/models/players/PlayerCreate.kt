package pt.isel.ls.api.models.players

import kotlinx.serialization.Serializable
import pt.isel.ls.utils.generateRandomEmail
import pt.isel.ls.utils.generateRandomPassword
import pt.isel.ls.utils.generateRandomString
import pt.isel.ls.utils.values.Email
import pt.isel.ls.utils.values.Password

@Serializable
data class PlayerCreate(
    val name: String = generateRandomString(),
    val email: Email = generateRandomEmail(),
    val password: Password = generateRandomPassword()
)
