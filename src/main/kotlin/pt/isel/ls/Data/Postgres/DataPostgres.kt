package pt.isel.ls.Data.Postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.Data.GameStorage
import pt.isel.ls.Data.GamingSessionStorage
import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Data.Storage
import pt.isel.ls.utils.postgres.runSQLScript

class DataPostgres(connectionString: String) : Storage {
    // TODO: Modify conn so that if there is an exception it rollbacks immediatelly (or commits if there's no exception to catch)

    private val source = PGSimpleDataSource().apply { setURL(connectionString) }

    private val conn = source.connection.also {
        it.autoCommit = false
    }

    fun create(){
        //TODO: Check all the paths are okay
        conn.runSQLScript("createSchema.sql")
        conn.runSQLScript("createGenres.sql")
    }

    override fun reset() {
        conn.runSQLScript("reset.sql")
    }

    override fun populate(){
        conn.runSQLScript("populate.sql")
    }


    override val players: PlayerStorage = PlayersPostgres(conn)

    override val gamingSessions: GamingSessionStorage = GamingSessionPostgres(conn)

    override val games: GameStorage = GamesPostgres(conn)
}