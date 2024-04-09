import Router from "./Router";
import {GamesSearchPage} from "../pages/games/GamesSearchPage";
import {GamesPage} from "../pages/games/GamesPage";
import {GamesDetailsPage} from "../pages/games/GamesDetailsPage";

const router = Router

router.addRouteHandler('/search',GamesSearchPage)
router.addRouteHandler('/',GamesPage)
router.addRouteHandler('/:id',GamesDetailsPage)

export default router