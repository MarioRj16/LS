package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.PlayerStorage
import pt.isel.ls.Domain.Player

class PlayersMem(val schema: DataMemSchema): PlayerStorage {
    override fun create(name: String, email: String): Int {
        TODO("Not yet implemented")
    }

    override fun search(id: Int): Player {
        TODO("Not yet implemented")
    }

}