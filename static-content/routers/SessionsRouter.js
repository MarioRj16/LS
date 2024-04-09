import Router from "./Router";
import {SessionsSearchPage} from "../pages/sessions/SessionsSearchPage";
import {SessionsPage} from "../pages/sessions/SessionsPage";
import {SessionsDetailsPage} from "../pages/sessions/SessionsDetailsPage";

const router = Router

router.addRouteHandler('/search',SessionsSearchPage)
router.addRouteHandler('/',SessionsPage)
router.addRouteHandler('/:id',SessionsDetailsPage)

export default router