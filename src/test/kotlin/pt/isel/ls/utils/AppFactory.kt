package pt.isel.ls.utils

import pt.isel.ls.data.mem.DataMem

internal abstract class AppFactory(private val db: DataMem) {
    val playerFactory = PlayerFactory(db)
    val gameFactory = GameFactory(db)
    val gamingSessionFactory = GamingSessionFactory(db)
}