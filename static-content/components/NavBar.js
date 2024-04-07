import {a, div, nav} from "../utils/Elements";

export async function NavBar(state){
    //TODO(GET USER)

    const navbar= await div(
        nav(
            a({class: "nav-link", href: "#"}, "Home"),
            a({class: "nav-link", href: "#games/search"}, "GamesSearch"),
            a({class: "nav-link", href: "#sessions/search",}, "SessionsSearch"),
            a({class: "nav-link", href: `#player/${playerId}`}, "PlayerDetails"),
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