package pt.isel.ls.utils

fun <T> List<T>.paginate(
    skip: Int,
    limit: Int,
): List<T> {
    require(skip >= 0) { "skip must be a non negative integer\nskip=$skip" }
    require(limit >= 0) { "Limit must be a non negative integer\nlimit=$limit" }
    if (this.isEmpty()) return emptyList()
    val lastIndex: Int = if (limit > size) size else limit + skip
    return subList(skip, lastIndex)
}

fun emailIsValid(email: String): Boolean {
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$")
    return emailRegex.matches(email)
}
