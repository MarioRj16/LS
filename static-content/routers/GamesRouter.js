import Router from "./router";

const router= Router

router.addRouteHandler('/search',GamesSearchPage)
router.addRouteHandler('/',GamesPage)
router.addRouteHandler('/:id',GameDetailsPage)

export default router