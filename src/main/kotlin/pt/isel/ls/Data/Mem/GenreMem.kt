package pt.isel.ls.Data.Mem

import pt.isel.ls.Data.GenreStorage

class GenreMem(val schema: DataMemSchema): GenreStorage {
    override fun create(genre: String): String {
        TODO("Not yet implemented")
    }

    override fun exists(genre: String): Boolean {
        TODO("Not yet implemented")
    }
}