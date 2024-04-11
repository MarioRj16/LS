import {Router} from "./Router.js";
import {PlayersDetailsPage} from "../pages/players/PlayersDetailsPage.js";
import {NotFoundPage} from "../pages/errors/NotFoundPage.js";

const router = Router()

router.addRouteHandler('/:id',PlayersDetailsPage)
router.addDefaultNotFoundRouteHandler(NotFoundPage)

export default router