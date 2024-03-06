package pt.isel.ls.models

data class Player(
    val ID: Int,
    val name: String,
    val email: String,
){
    init {
        require('@' !in email){"$email has to include an @"}
    }
}