package pt.isel.ls.data.mem

import java.util.concurrent.atomic.AtomicInteger

class DataMemTable<T> {
    val table =
        object : HashMap<Int, T>() {
            override fun put(
                key: Int,
                value: T,
            ): T? {
                nextId.incrementAndGet()
                return super.put(key, value)
            }
        }
    var nextId = AtomicInteger(1)
        private set
}
