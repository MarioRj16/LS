import {button, div, form, h1, input, label, option, select} from "../../utils/Elements.js";
import {SearchSessions} from "../../components/sessions/SearchSessions.js";

export async function SessionsSearchPage(state){

    return div({class: "card mx-auto justify-content-center w-50 maxH-50"},
        div({class: "card-header"},
            h1({}, "Sessions Search"),
        ),
        div({class: "card-body"},
        form(
        { class: "sessions-search-container d-flex flex-column gap-4", onsubmit: "#"},
            div(
                label({ class:"form-label", for: "gameInput" }, "Game ID"),
                input({ class:"form-control", type: "number", id: "gameInput", placeholder: "Game ID", min: 1, required: true}),
            ),
            div(
                { class: "search-inputs" },
                label({ class:"form-label", for: "dateInput" }, "Date and time"),
                input({ class:"form-control", type: "datetime-local", id: "dateInput", placeholder: "Date and time" }),
            ),
            div(
                label({ class:"form-label", for: "playerIdInput" }, "Player ID"),
                input({ class:"form-control", type: "number", id: "playerIdInput", placeholder: "Player ID", min: 1 }),
            ),
            div({class: "form-floating"},
                select({ class:"form-select", id: "stateInput" },
                    option({ selected: true }, "All"),
                    option({ value: "true" }, "open"),
                    option({ value: "false" }, "closed"),
                 )
            ),
            div({class: "mx-auto"},
                button({ class: "btn btn-primary", type: "button", onclick: SearchSessions }, "Search")
            )
        )
    ));

}