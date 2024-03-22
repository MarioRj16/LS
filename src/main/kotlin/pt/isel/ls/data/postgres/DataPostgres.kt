package pt.isel.ls.data.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.data.GameStorage
import pt.isel.ls.data.GamingSessionStorage
import pt.isel.ls.data.PlayerStorage
import pt.isel.ls.data.Storage
import pt.isel.ls.utils.postgres.runSQLScript

class DataPostgres(connectionString: String) : Storage {
    private val dataSource = PGSimpleDataSource().apply { setURL(connectionString) }

    private val conn = dataSource.connection.also {
        it.autoCommit = false
    }

    fun create() {
        conn.runSQLScript("createSchema.sql")
        conn.runSQLScript("createGenres.sql")
    }

    override fun reset() {
        conn.runSQLScript("reset.sql")
    }

    override fun populate() {
        conn.runSQLScript("populate.sql")
    }

    override val players: PlayerStorage = PlayersPostgres(conn)

    override val gamingSessions: GamingSessionStorage = GamingSessionPostgres(conn)

    override val games: GameStorage = GamesPostgres(conn)
}