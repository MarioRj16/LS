package pt.isel.ls.services

import pt.isel.ls.api.models.genres.GenreListResponse
import pt.isel.ls.data.Data
import java.util.*

class GenresServices(data: Data) : ServicesSchema(data) {
    fun getGenres(token: UUID): GenreListResponse =
        withAuthorization(token) {
            return@withAuthorization GenreListResponse(data.genres.getAllGenres())
        }
}
