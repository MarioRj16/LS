package pt.isel.ls.utils

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UtilsTests {
    @Test
    fun `paginate() does pagination correctly`() {
        val intList = List(5) { it }

        assertEquals(intList, intList.paginate(0, 5))
        assertTrue(intList.paginate(2, 0).isEmpty())
        assertTrue(intList.paginate(5, 0).isEmpty())
        assertEquals(intList.drop(2), intList.paginate(2, 3))
    }

    @Test
    fun `paginate() throws exception if limit or skip are not valid`() {
        val l = List(10) { it }
        assertThrows<IllegalArgumentException> {
            l.paginate(-1, 2)
        }
        assertThrows<IllegalArgumentException> {
            l.paginate(2, -1)
        }
        assertThrows<IllegalArgumentException> {
            l.paginate(-1, -2)
        }
    }
}
