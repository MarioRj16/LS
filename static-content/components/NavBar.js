import {a, div, nav} from "../utils/Elements.js";

export async function NavBar(state){
    //TODO(GET USER)

    const navbar= await div(
        nav(
            {class: "navbar navbar-expand-lg bg-body-tertiary"},
            a({class: "nav-link", href: "#home"}, "Home"),
            a({class: "nav-link", href: "#games/search"}, "GamesSearch"),
            a({class: "nav-link", href: "#sessions/search",}, "SessionsSearch"),
           // a({class: "nav-link", href: `#player/${playerId}`}, "PlayerDetails"),
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