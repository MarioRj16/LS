// noinspection ALL

import {Router} from "../routers/Router";
import {PlayerHomePage} from "../pages/PlayerHomePage";
import {PlayersDetailsPage} from "../pages/players/PlayersDetailsPage";
import {GamesPage} from "../pages/games/GamesPage";
import {GamesSearchPage} from "../pages/games/GamesSearchPage";
import {GamesDetailsPage} from "../pages/games/GamesDetailsPage";
import {SessionsPage} from "../pages/sessions/SessionsPage";
import {SessionsSearchPage} from "../pages/sessions/SessionsSearchPage";
import {SessionsDetailsPage} from "../pages/sessions/SessionsDetailsPage";

describe('router', function () {
    describe('Router tests', function () {
        describe('Router tests', function () {
            const router = Router()

            it('should find correct handler', function () {
                router.addRouteHandler("/home", PlayerHomePage)
                router.addRouteHandler("/player/:id", PlayersDetailsPage)
                router.addRouteHandler("/games", GamesPage)
                router.addRouteHandler("/games/search", GamesSearchPage)
                router.addRouteHandler("/games/:id", GamesDetailsPage)
                router.addRouteHandler("/sessions", SessionsPage)
                router.addRouteHandler("/sessions/search", SessionsSearchPage)
                router.addRouteHandler("/sessions/:id", SessionsDetailsPage)

                const homeHandler = router.getHandler("/home")
                const playerDetailsHandler = router.getHandler("/player/10")
                const gamesHandler = router.getHandler("/games")
                const gamesSearchHandler = router.getHandler("/games/search")
                const gamesDetailsHandler = router.getHandler("/games/10")
                const sessionsHandler = router.getHandler("/sessions")
                const sessionsSearchHandler = router.getHandler("/sessions/search")
                const sessionsDetailsHandler = router.getHandler("/sessions/10")

                homeHandler.name.should.be.equal("PlayerHomePage")
                playerDetailsHandler.name.should.be.equal("PlayersDetailsPage")
                gamesHandler.name.should.be.equal("GamesPage")
                gamesSearchHandler.name.should.be.equal("GamesSearchPage")
                gamesDetailsHandler.name.should.be.equal("GamesDetailsPage")
                sessionsHandler.name.should.be.equal("SessionsPage")
                sessionsSearchHandler.name.should.be.equal("SessionsSearchPage")
                sessionsDetailsHandler.name.should.be.equal("SessionsDetailsPage")
            })

            it('should throw error for invalid route', function () {
                expect(() => router.getHandler("invalid")).to.throw(Error)
            })
        })

    })
});