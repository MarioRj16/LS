import {a, div, nav} from "../utils/Elements.js";
import {USER_ID} from "../utils/Configs.js";

export async function NavBar(state){
    //TODO(GET USER)

    const navbar= await div(
        nav(
            {class: "nav nav-pills"},
            a({class: "h4 nav-link", href: "#home"}, "Home"),
            a({class: "h4 nav-link", href: "#games/search"}, "GamesSearch"),
            a({class: "h4 nav-link", href: "#sessions/search",}, "SessionsSearch"),
            a({class: "h4 nav-link", href: `#players/${USER_ID}`}, "PlayerDetails"),
        )
    )
    let active = window.location.hash;
    if (active === "")
        active = "#";

    const selectedNav = navbar.querySelector(`a[href="${active}"]`)
    if(selectedNav != null)
        selectedNav.classList.add("active");

    return navbar;
}