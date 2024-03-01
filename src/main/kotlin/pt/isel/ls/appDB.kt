package pt.isel.ls

import org.postgresql.ds.PGSimpleDataSource

fun main(){

    val dataSource = PGSimpleDataSource()
    val jdbcDatabaseURL = System.getenv("JDBC_DATABASE_URL")
    dataSource.setURL(jdbcDatabaseURL)
    val conn = dataSource.connection
    conn.autoCommit = true
    try{
        val stm = conn.prepareStatement("select * from players")
        val rs = stm.executeQuery()
        while (rs.next()) {
            println(rs.getString("email"))
        }
    } catch (e: Exception){
        conn.rollback()
    } finally {
        conn.close()
    }
}