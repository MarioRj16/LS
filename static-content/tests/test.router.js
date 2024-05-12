// noinspection ALL

import {Router} from "../routers/Router.js";
import {PlayerHomePage} from "../pages/PlayerHomePage.js";
import {PlayersDetailsPage} from "../pages/players/PlayersDetailsPage.js";
import {GamesPage} from "../pages/games/GamesPage.js";
import {GamesSearchPage} from "../pages/games/GamesSearchPage.js";
import {GamesDetailsPage} from "../pages/games/GamesDetailsPage.js";
import {SessionsPage} from "../pages/sessions/SessionsPage.js";
import {SessionsSearchPage} from "../pages/sessions/SessionsSearchPage.js";
import {SessionsDetailsPage} from "../pages/sessions/SessionsDetailsPage.js";
import {GamesCreatePage} from "../pages/games/GamesCreatePage.js";
import {SessionsUpdatePage} from "../pages/sessions/SessionsUpdatePage.js";
import {SessionsCreatePage} from "../pages/sessions/SessionsCreatePage.js";


describe('router', function () {
    describe('Router tests', function () {
        describe('Router tests', function () {
            const router = Router()

            it('should find correct handler', function () {
                router.addRouteHandler("/home", PlayerHomePage)

                router.addRouteHandler("/player/:id", PlayersDetailsPage)

                router.addRouteHandler("/games/search", GamesSearchPage)
                router.addRouteHandler("/games/create", GamesCreatePage)
                router.addRouteHandler("/games/:id", GamesDetailsPage)
                router.addRouteHandler("/games", GamesPage)

                router.addRouteHandler("/sessions/search", SessionsSearchPage)
                router.addRouteHandler("/sessions/create", SessionsCreatePage)
                router.addRouteHandler("/sessions/:id/update", SessionsUpdatePage)
                router.addRouteHandler("/sessions/:id", SessionsDetailsPage)
                router.addRouteHandler("/sessions", SessionsPage)

                const homeHandler = router.getHandler("/home")

                const playerDetailsHandler = router.getHandler("/player/10")

                const gamesSearchHandler = router.getHandler("/games/search")
                const gamesCreateHandler = router.getHandler("/games/create")
                const gamesDetailsHandler = router.getHandler("/games/10")
                const gamesHandler = router.getHandler("/games")

                const sessionsHandler = router.getHandler("/sessions")
                const sessionsSearchHandler = router.getHandler("/sessions/search")
                const sessionsCreateHandler = router.getHandler("/sessions/create")
                const sessionsDetailsHandler = router.getHandler("/sessions/10")
                const sessionsUpdateHandler = router.getHandler("/sessions/10/update")

                //HOME
                homeHandler.handlerPath.should.be.equal("/home")
                homeHandler.handler.should.be.equal(PlayerHomePage)

                //PLAYERS
                playerDetailsHandler.handlerPath.should.be.equal("/player/:id")
                playerDetailsHandler.handler.should.be.equal(PlayersDetailsPage)

                //GAMES
                gamesHandler.handlerPath.should.be.equal("/games")
                gamesHandler.handler.should.be.equal(GamesPage)

                gamesSearchHandler.handlerPath.should.be.equal("/games/search")
                gamesSearchHandler.handler.should.be.equal(GamesSearchPage)

                gamesCreateHandler.handlerPath.should.be.equal("/games/create")
                gamesCreateHandler.handler.should.be.equal(GamesCreatePage)

                gamesDetailsHandler.handlerPath.should.be.equal("/games/:id")
                gamesDetailsHandler.handler.should.be.equal(GamesDetailsPage)

                //SESSIONS
                sessionsHandler.handlerPath.should.be.equal("/sessions")
                sessionsHandler.handler.should.be.equal(SessionsPage)

                sessionsSearchHandler.handlerPath.should.be.equal("/sessions/search")
                sessionsSearchHandler.handler.should.be.equal(SessionsSearchPage)

                sessionsCreateHandler.handlerPath.should.be.equal("/sessions/create")
                sessionsCreateHandler.handler.should.be.equal(SessionsCreatePage)

                sessionsDetailsHandler.handlerPath.should.be.equal("/sessions/:id")
                sessionsDetailsHandler.handler.should.be.equal(SessionsDetailsPage)

                sessionsUpdateHandler.handlerPath.should.be.equal("/sessions/:id/update")
                sessionsUpdateHandler.handler.should.be.equal(SessionsUpdatePage)

            })

            it('should throw error for invalid route', function () {
                const result= router.getHandler("invalid")
                if(result!=undefined)
                    throw new Error("Should throw error")

            })

        })

    })
});