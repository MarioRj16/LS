import router from "./routers/Router.js";
import handlers from "./handlers.js";
import PlayersRouter from "./routers/PlayersRouter";
import GamesRouter from "./routers/GamesRouter";
import SessionsRouter from "./routers/SessionsRouter";
import {PlayerHomePage} from "./pages/PlayerHomePage";
import {NotFoundPage} from "./pages/errors/NotFoundPage";
import {parseUrl, render} from "./utils/Render";
import {div, hr} from "./utils/Elements";
import {NavBar} from "./components/NavBar"
import {Consistent} from "./pages/Consistent";
import Router from "./routers/Router.js";

// For more information on ES6 modules, see https://www.javascripttutorial.net/es6/es6-modules/ or
// https://www.w3schools.com/js/js_modules.asp

//window.addEventListener('load', loadHandler)
window.addEventListener('load', hashChangeHandler)
window.addEventListener('hashchange', hashChangeHandler)
/*
function loadHandler(state,error){

    router.addRouteHandler("/home",PlayerHomePage)
    router.addRouteHandler("/players",PlayersRouter)
    router.addRouteHandler("/games",GamesRouter)
    router.addRouteHandler("/sessions",SessionsRouter)
    router.addDefaultNotFoundRouteHandler(NotFoundPage)

    hashChangeHandler()
}


 */
function hashChangeHandler(){
    console.log("OI")
    const path =  window.location.hash.replace("#", "/")
    const state = parseUrl(path)
   // const maincontenteddd= document.getElementById("mainContent")
    //const handler= router.getRouteHandler(path)
   // handler(maincontenteddd)

    console.log(state)
    Consistent(state, Router)
        .then(render)

}

