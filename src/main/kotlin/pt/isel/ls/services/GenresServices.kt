package pt.isel.ls.services

import java.util.*
import pt.isel.ls.api.models.genres.GenreListResponse
import pt.isel.ls.data.Data

class GenresServices(data: Data) : ServicesSchema(data) {
    fun getGenres(token: UUID): GenreListResponse =
        withAuthorization(token){
            return@withAuthorization GenreListResponse(data.genres.getAllGenres())
        }
}