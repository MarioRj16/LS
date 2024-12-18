package pt.isel.ls

import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val CONN_NAME = "JDBC_CONN"
const val DEFAULT_SKIP = 0
const val DEFAULT_LIMIT = 30
const val PORT = 8000
const val TEST_PORT = 3000
const val SESSION_MAX_CAPACITY = 32
const val SESSION_MIN_CAPACITY = 2

val logger: Logger = LoggerFactory.getLogger("pt.isel.ls")
