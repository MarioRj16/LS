import router from "./routers/router.js";
import handlers from "./handlers.js";
import PlayersRouter from "./routers/PlayersRouter";
import GamesRouter from "./routers/GamesRouter";
import SessionsRouter from "./routers/SessionsRouter";
import {PlayerHomePage} from "./pages/PlayerHomePage";
import {NotFoundPage} from "./pages/errors/NotFoundPage";
import {parseUrl} from "./utils/Render";
import {div} from "./utils/Elements";
import {NavBar} from "./components/NavBar"

// For more information on ES6 modules, see https://www.javascripttutorial.net/es6/es6-modules/ or
// https://www.w3schools.com/js/js_modules.asp

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler(){

    router.addRouteHandler("/",PlayerHomePage)
    router.addRouteHandler("/players",PlayersRouter)
    router.addRouteHandler("/games",GamesRouter)
    router.addRouteHandler("/sessions",SessionsRouter)
    router.addDefaultNotFoundRouteHandler(NotFoundPage)

    hashChangeHandler()
}

function hashChangeHandler(){

    const mainContent = document.getElementById("mainContent")
    const path =  window.location.hash.replace("#", "/")
    const state = parseUrl(path)

    const handler = router.getRouteHandler(path)
    div(
        NavBar(state),
        handler(mainContent,state)
    )



}

