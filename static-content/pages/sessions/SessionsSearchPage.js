import { button, div, form, h1, input, label, option, select } from "../../utils/Elements.js";

export async function SessionsSearchPage(state) {
    function handleFormSubmit(event) {
        event.preventDefault(); // Prevent default form submission behavior

        const gameInput = document.getElementById('gameInput').value;
        const dateInput = document.getElementById('dateInput').value;
        const stateInput = document.getElementById('stateInput').checked;
        const playerIdInput = document.getElementById('playerIdInput').value;

        const searchCriteria = {
            game: gameInput ? parseInt(gameInput) : null,
            date: dateInput ? new Date(dateInput) : null,
            state: stateInput,
            playerId: playerIdInput ? parseInt(playerIdInput) : null
        };
        window.location.href=`/pessoa`
        //window.location.href = `#sessions?${new URLSearchParams(searchCriteria).toString()}`;
    }

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Sessions Search"),
        ),
        div(
            { class: "card-body" },
            form(
                {
                    class: "sessions-search-container d-flex flex-column gap-4",
                    onsubmit: handleFormSubmit // Use the function to handle form submission
                },
                div(
                    {},
                    label({ class: "form-label", for: "gameInput" }, "Game ID"),
                    input({ class: "form-control", type: "number", id: "gameInput", placeholder: "Game ID", min: 1, required: true })
                ),
                div(
                    {},
                    label({ class: "form-label", for: "dateInput" }, "Date and time"),
                    input({ class: "form-control", type: "datetime-local", id: "dateInput", placeholder: "Date and time" })
                ),
                div(
                    {},
                    label({ class: "form-label", for: "playerIdInput" }, "Player ID"),
                    input({ class: "form-control", type: "number", id: "playerIdInput", placeholder: "Player ID", min: 1 })
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
                    button({ class: "btn btn-primary", type: "submit" }, "Search")
                )
            )
        )
    );
}
