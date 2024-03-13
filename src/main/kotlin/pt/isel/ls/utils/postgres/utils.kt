package pt.isel.ls.utils.postgres

import java.io.File
import java.sql.Connection

fun Connection.runSQLScript(path: String){
    val script = File(path).readText()
    prepareStatement(script).executeUpdate()
}
