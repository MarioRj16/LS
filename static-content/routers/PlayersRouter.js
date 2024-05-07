import {Router} from "./Router.js";
import {PlayersDetailsPage} from "../pages/players/PlayersDetailsPage.js";

const router = Router()

router.addRouteHandler('/:id',PlayersDetailsPage)
//router.addDefaultNotFoundRouteHandler(NotFoundPage)

export default router