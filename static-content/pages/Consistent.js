import Router from "../routers/Router";
import {PlayerHomePage} from "./PlayerHomePage";
import PlayersRouter from "../routers/PlayersRouter";
import GamesRouter from "../routers/GamesRouter";
import SessionsRouter from "../routers/SessionsRouter";
import {NotFoundPage} from "./errors/NotFoundPage";
import {div, hr} from "../utils/Elements";
import {NavBar} from "../components/NavBar";


export async function Consistent(state,router){


router.addRouteHandler("/home",PlayerHomePage)
router.addRouteHandler("/players",PlayersRouter)
router.addRouteHandler("/games",GamesRouter)
router.addRouteHandler("/sessions",SessionsRouter)
router.addDefaultNotFoundRouteHandler(NotFoundPage)

    return div(
        NavBar(state),
        hr(),
       router
       // router(state)
    )
}