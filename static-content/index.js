import {parseUrl, render} from "./utils/Render.js";
import {Consistent} from "./pages/Consistent.js";

// For more information on ES6 modules, see https://www.javascripttutorial.net/es6/es6-modules/ or
// https://www.w3schools.com/js/js_modules.asp

window.addEventListener('load', hashChangeHandler)
window.addEventListener('hashchange', hashChangeHandler)

function hashChangeHandler(){

    const path = window.location.hash.replace("#", "/")
    const state = parseUrl(path)
    Consistent(state)
        .then(render)

}

