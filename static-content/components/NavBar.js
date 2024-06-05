import {a, div, nav} from "../utils/Elements.js";
import {getStoredUser} from "../utils/Utils.js";

export async function NavBar(state){
    const user = getStoredUser();

    const navbar = await div(
        nav(
            {class: "nav nav-pills"},
            a({class: "h4 nav-link", href: "#home"}, "Home"),
            a({class: "h4 nav-link", href: "#games/search"}, "GamesSearch"),
            a({class: "h4 nav-link", href: "#sessions/search"}, "SessionsSearch"),
            user == null
                ? div({class: "nav ms-auto"},
                    a({class: "h4 nav-item nav-link", href: "#register"}, "Register"),
                    a({class: "h4 nav-item nav-link", href: "#login"}, "Login")
                )
                : div({class: "nav ms-auto"},
                    a({class: "h4 nav-item nav-link", id: "logout-link", href: "#logout"}, "Logout"),
                    a({class: "h4 nav-item nav-link", href: `#players/${user.playerId}`}, "Profile")
                )
        )
    );

    // Handle the logout link click
    const logoutLink = navbar.querySelector("#logout-link");
    if (logoutLink) {
        logoutLink.addEventListener("click", (event) => {
            event.preventDefault();
            sessionStorage.removeItem("user");
            window.location.href = `#login`;
        });
    }

    // Set the active class on the current page's nav link
    let active = window.location.hash;
    if (active === "")
        active = "#";

    const selectedNav = navbar.querySelector(`a[href="${active}"]`);
    if (selectedNav != null)
        selectedNav.classList.add("active");

    return navbar;
}
