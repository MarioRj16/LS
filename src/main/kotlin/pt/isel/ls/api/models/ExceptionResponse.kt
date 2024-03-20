package pt.isel.ls.api.models

import kotlinx.serialization.Serializable

@Serializable
data class ExceptionResponse(val message: String)