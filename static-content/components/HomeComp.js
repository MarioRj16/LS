import {button, div, h1, h2} from "../utils/Elements.js";

export async function HomeComp(redirectGameCreate, redirectSessionCreate) {

    const gameCreateButton = button({ class: "btn btn-primary", type: "submit" }, "Go");
    (await gameCreateButton).addEventListener('click', redirectGameCreate);

    const sessionCreateButton = button({ class: "btn btn-primary", type: "submit" }, "Go");
    (await sessionCreateButton).addEventListener('click', redirectSessionCreate);

    return div(
        {class: "card mx-auto justify-content-center w-50 maxH-50"},
        h1({class: "card-header text-center"}, "PlayerHome" ),
        div(
            {class: "card-body d-flex justify-content-between"},
            div(
                {class: "text-center"},
                h2("Create a Game"),
                gameCreateButton
            ),
            div(
                {class: "text-center"},
                h2("Create a Session"),
                sessionCreateButton
            )
        )
    )
}