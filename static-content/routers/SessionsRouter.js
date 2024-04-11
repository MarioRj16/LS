import {Router} from "./Router.js";
import {SessionsSearchPage} from "../pages/sessions/SessionsSearchPage.js";
import {SessionsPage} from "../pages/sessions/SessionsPage.js";
import {SessionsDetailsPage} from "../pages/sessions/SessionsDetailsPage.js";
import {NotFoundPage} from "../pages/errors/NotFoundPage.js";

const router = Router()

router.addRouteHandler('/search',SessionsSearchPage)
router.addRouteHandler('/',SessionsPage)
router.addRouteHandler('/:id',SessionsDetailsPage)
router.addDefaultNotFoundRouteHandler(NotFoundPage)

export default router