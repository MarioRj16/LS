package pt.isel.ls.utils.postgres

import java.io.File
import java.sql.Connection

/**
 * Executes an SQL script located at the specified path using this Connection.
 *
 * @param path The path to the SQL script.
 * @throws SQLException If an error occurs while executing the SQL script.
 */
@Suppress("KDocUnresolvedReference")
fun Connection.runSQLScript(path: String) {
    val script = File("src/main/sql/$path").readText()
    prepareStatement(script).executeUpdate()
}

/**
 * Executes the specified block of code using this Connection, ensuring that changes are rolled back if an exception occurs.
 *
 * The Connection is automatically committed and closed after the block has been executed or if an exception is thrown.
 *
 * @param block The block of code to execute with this Connection.
 * @return The result of the block execution.
 * @throws Throwable If an exception occurs during the execution of the block, the changes are rolled back and the exception is rethrown.
 */
inline fun <R> Connection.useWithRollback(block: (Connection) -> R): R {
    try {
        return block(this)
    } catch (e: Throwable) {
        rollback()
        throw e
    } finally {
        commit()
        close()
    }
}
