package pt.isel.ls.utils.exceptions

class AuthorizationException(message: String) : Exception(message)

class ForbiddenException(message: String) : Exception(message)

class ConflictException(message: String) : Exception(message)