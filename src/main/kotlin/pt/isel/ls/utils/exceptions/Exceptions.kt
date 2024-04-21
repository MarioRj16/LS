package pt.isel.ls.utils.exceptions

class AuthorizationException(message: String = "Missing or invalid Bearer token") : Exception(message)

class ForbiddenException(message: String) : Exception(message)

class BadRequestException(message: String) : Exception(message)
