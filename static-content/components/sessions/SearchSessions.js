import {button, div, h1, input, label, option, select} from "../../utils/Elements.js";

export async function SearchSessions(games,players,handleFormSubmit){

    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Search");
    (await submitButton).addEventListener('click', handleFormSubmit);



    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Sessions Search")
        ),
        div(
            { class: "card-body d-flex flex-column gap-4" },
            div(
                {},
                label({ class: "form-label", for: "gameInput" }, "Game "),
                games
            ),
            div(
                {},
                label({ class: "form-label", for: "dateInput" }, "Date and time"),
                input({ class: "form-control", type: "datetime-local", id: "dateInput", placeholder: "Date and time" })
            ),
            div(
                {},
                label({ class: "form-label", for: "player" }, "Player Name"),
                players
            ),
            div(
                {},
                label({ class: "form-label", for: "stateInput" }, "Session State"),
                select({ class: "form-select", id: "stateInput" },
                    option({ value: "" }, "All"),
                    option({ value: "true" }, "Open"),
                    option({ value: "false" }, "Closed")
                )
            ),
            div(
                { class: "mx-auto" },
                submitButton
            )
        )
    );
}