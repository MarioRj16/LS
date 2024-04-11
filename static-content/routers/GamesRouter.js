import {Router} from "./Router.js";
import {GamesSearchPage} from "../pages/games/GamesSearchPage.js";
import {GamesPage} from "../pages/games/GamesPage.js";
import {GamesDetailsPage} from "../pages/games/GamesDetailsPage.js";
import {NotFoundPage} from "../pages/errors/NotFoundPage.js";

const router = Router()

router.addRouteHandler('/search',GamesSearchPage)
router.addRouteHandler('/',GamesPage)
router.addRouteHandler('/:id',GamesDetailsPage)
router.addDefaultNotFoundRouteHandler(NotFoundPage)
export default router