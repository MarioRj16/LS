import {button, div, h1, input} from "../../utils/Elements";
import {SearchSessions} from "../../components/sessions/SearchSessions";

export async function SessionsSearchPage(state){

    return div(
        { class: "sessions-search-container" },
        h1({}, "Search Sessions"),
        div(
            { class: "search-inputs" },
            input({ type: "number", id: "gameInput", placeholder: "Game ID" }),
            input({ type: "datetime-local", id: "dateInput", placeholder: "Date and time" }),
            div(
                { class: "checkbox-input" },
                input({ type: "checkbox", id: "stateInput" }),
               // "State"
            ),
            input({ type: "number", id: "playerIdInput", placeholder: "Player ID" }),
            button({ onclick: SearchSessions }, "Search")
        ),
    );

}