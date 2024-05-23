@file:Suppress("unused")

package pt.isel.ls.data.postgres

import org.postgresql.ds.PGSimpleDataSource
import pt.isel.ls.data.Data
import pt.isel.ls.data.GamesData
import pt.isel.ls.data.PlayersData
import pt.isel.ls.data.SessionsData
import pt.isel.ls.utils.postgres.runSQLScript

@Suppress("unused")
class DataPostgres(connectionString: String) : Data {
    private val dataSource = PGSimpleDataSource().apply { setURL(connectionString) }

    private fun conn() =
        dataSource.connection.also {
            it.autoCommit = false
        }

    fun create() {
        conn().runSQLScript("createSchema.sql")
        conn().runSQLScript("insertGenres.sql")
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

    override val gamingSessions: SessionsData = SessionsPostgres(::conn)

    override val games: GamesData = GamesPostgres(::conn)

    override val genres: GenresPostgres = GenresPostgres(::conn)
}
