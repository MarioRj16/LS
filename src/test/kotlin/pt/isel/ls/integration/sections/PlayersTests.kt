
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import pt.isel.ls.api.models.PlayerCreate
import pt.isel.ls.integration.IntegrationTests
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayersTests:IntegrationTests(){

    @Test
    fun createPlayer(){
        val requestBody = PlayerCreate("diferente","diferente@gmail.com")
        val request = Request(Method.POST, "$URI_PREFIX/player")
            .json(requestBody)
        client(request)
            .apply{
                assertEquals(Status.CREATED,status)
            }
    }

    @Test
    fun getPlayer(){
        val request = Request(Method.GET, "$URI_PREFIX/player/${user!!.playerId}")
            .json("")
            .token(user!!.token)
        client(request)
            .apply {
                assertEquals(Status.OK,status)
            }
    }


}