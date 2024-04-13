import {Router} from "../routers/Router.js";
import {PlayerHomePage} from "./PlayerHomePage.js";
import PlayersRouter from "../routers/PlayersRouter.js";
import GamesRouter from "../routers/GamesRouter.js";
import SessionsRouter from "../routers/SessionsRouter.js";
import {NotFoundPage} from "./errors/NotFoundPage.js";
import {div, hr} from "../utils/Elements.js";
import {NavBar} from "../components/NavBar.js";


const router = Router()

router.addRouteHandler("/home",PlayerHomePage)
router.addRouteHandler("/players",PlayersRouter)
router.addRouteHandler("/games",GamesRouter)
router.addRouteHandler("/sessions",SessionsRouter)
router.addDefaultNotFoundRouteHandler(NotFoundPage)

export async function Consistent(state){
    return div(
        NavBar(state),
        hr(),
        router(state)
    )
}
