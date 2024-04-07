import Router from "./router";

const router= Router

router.addRouteHandler('/search',SessionsSearchPage)
router.addRouteHandler('/',SessionsPage)
router.addRouteHandler('/:id',SessionsDetailsPage)

export default router