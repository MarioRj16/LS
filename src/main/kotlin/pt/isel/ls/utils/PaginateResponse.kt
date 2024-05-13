package pt.isel.ls.utils

class PaginateResponse<T> private constructor(
    val element: List<T>,
    val hasNext: Boolean,
    val hasPrevious: Boolean
) {
    companion object {
        fun <T> fromList(list: List<T>, skip: Int, limit: Int): PaginateResponse<T> {
            val hasNext = skip + limit < list.size
            val hasPrevious = skip > 0
            val sublist = list.paginate(skip, limit)
            return PaginateResponse(sublist, hasNext, hasPrevious)
        }
    }

    operator fun component1() = element

    operator fun component2() = hasNext

    operator fun component3() = hasPrevious
}