package pt.isel.ls.data.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.data.Data
import pt.isel.ls.data.GamesData
import pt.isel.ls.data.GamingSessionsData
import pt.isel.ls.data.PlayersData
import pt.isel.ls.utils.postgres.runSQLScript

class DataPostgres(connectionString: String) : Data {
    private val dataSource = PGSimpleDataSource().apply { setURL(connectionString) }

    private fun conn() =
        dataSource.connection.also {
            it.autoCommit = false
        }

    fun create() {
        conn().runSQLScript("createSchema.sql")
        conn().runSQLScript("createGenres.sql")
    }

    fun delete() {
        conn().runSQLScript("deleteSchema.sql")
    }

    override fun reset() {
        conn().runSQLScript("reset.sql")
    }

    fun populate() {
        conn().runSQLScript("populate.sql")
    }

    override val players: PlayersData = PlayersPostgres(::conn)

    override val gamingSessions: GamingSessionsData = GamingSessionPostgres(::conn)

    override val games: GamesData = GamesPostgres(::conn)
}
