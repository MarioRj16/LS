package pt.isel.ls.data

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pt.isel.ls.data.mem.DataMem
import pt.isel.ls.domain.Genre
import pt.isel.ls.utils.currentLocalDateTime
import kotlin.test.assertEquals

class GamingSessionTests: DataMem() {


    @Test
    fun `Gaming Session can be created`(){
        games.create("name", "name", setOf(Genre(genre = "FPS")))
        val session = gamingSessions.create(
            capacity = 1,
            game = 1,
            date = LocalDateTime(
                LocalDate(2050, 3, 3),
                LocalTime(1, 1, 1, 1)
            ))
        val expected = gamingSessions.get(1)
        
        assertEquals(expected, session)
    }

    @Test
    fun `Integrity Restritions are enforced`(){
        // TODO: Should we write tests that check if the foreign keys are valid???
        assertThrows<IllegalArgumentException> {
            gamingSessions.create(capacity = -542, game = 1, date = currentLocalDateTime())
        }

        assertThrows<IllegalArgumentException> {
            gamingSessions.create(
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