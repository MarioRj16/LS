package pt.isel.ls.Data.Mem

class DBTableMem<T> {
    val table = object: HashMap<Int, T>() {
        override fun put(key: Int, value: T): T? {
            nextId++
            return super.put(key, value)
        }
    }
    var nextId = 1
        private set
}