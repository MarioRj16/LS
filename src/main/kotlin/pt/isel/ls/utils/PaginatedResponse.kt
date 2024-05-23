package pt.isel.ls.utils

class PaginatedResponse<T> private constructor(
    val element: List<T>,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
) {
    companion object {
        fun <T> fromList(list: List<T>, skip: Int, limit: Int): PaginatedResponse<T> {
            val hasNext = skip + limit < list.size
            val hasPrevious = skip > 0
            return PaginatedResponse(list.paginate(skip, limit), hasNext, hasPrevious)
        }

        private fun <T> List<T>.paginate(
            skip: Int,
            limit: Int,
        ): List<T> {
            require(skip >= 0) { "skip must be a non negative integer\nskip=$skip" }
            require(limit >= 0) { "Limit must be a non negative integer\nlimit=$limit" }
            if (this.isEmpty() || skip >= size) {
                return emptyList()
            }
            val lastIndex: Int = if (limit + skip > size) size else limit + skip
            return subList(skip, lastIndex)
        }
    }

    operator fun component1() = element

    operator fun component2() = hasNext

    operator fun component3() = hasPrevious
}
