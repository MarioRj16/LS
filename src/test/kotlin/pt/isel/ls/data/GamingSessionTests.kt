package pt.isel.ls.data

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.Data.Mem.DataMem
import pt.isel.ls.Data.Mem.DataMemSchema
import pt.isel.ls.Domain.Genre
import pt.isel.ls.utils.currentLocalDateTime
import kotlin.test.assertEquals

class GamingSessionTests {
    val db = DataMem(DataMemSchema())
    val game = db.games.create("name", "name", setOf(Genre(genre = "FPS")))

    @Test
    fun `Gaming Session can be created`(){
        val session = db.gamingSessions.create(
            capacity = 1,
            game = 1,
            date = LocalDateTime(
                LocalDate(2050, 3, 3),
                LocalTime(1, 1, 1, 1)
            ))
        assertEquals(db.gamingSessions.get(1), session)
    }

    @Test
    fun `Integrity Restritions are enforced`(){
        // TODO: Should we write tests that check if the foreign keys are valid???
        assertThrows<IllegalArgumentException> {
            db.gamingSessions.create(capacity = -542, game = 1, date = currentLocalDateTime())
        }

        assertThrows<IllegalArgumentException> {
            db.gamingSessions.create(
                capacity = 10,
                game = 1,
                date = LocalDateTime(
                LocalDate(1900, 3, 3),
                LocalTime(1, 1, 1, 1)
                )
            )
        }

    }
}