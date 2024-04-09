import Router from "./Router";
import {PlayersDetailsPage} from "../pages/players/PlayersDetailsPage";

const router = Router

router.addRouteHandler('/:id',PlayersDetailsPage)

export default router