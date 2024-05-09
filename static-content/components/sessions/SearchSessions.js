import {button, div, h1, input, label, option, select} from "../../utils/Elements.js";

export async function SearchSessions(games,players){

    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Search");
    (await submitButton).addEventListener('click', handleFormSubmit);

    function handleFormSubmit(event) {
        event.preventDefault();

        //if there's something in the input field but it doesn't correlate to any game
        let gameInput = document.getElementById('gameInput').value;
        try {
            gameInput = document.getElementById(gameInput).accessKey;
        } catch (error) {
            gameInput = null;
        }
        const dateInput = document.getElementById('dateInput').value;
        const stateInput = document.getElementById('stateInput').value;
        const playerInput = document.getElementById('playerInput').value;

        const enteredDate = new Date(dateInput);
        const earliestDate = new Date("1970-01-01T00:00:00");
        if (enteredDate < earliestDate) {
            alert("Please enter a date and time after January 1, 1970.");
            return;
        }

        const searchCriteria = {};

        if (gameInput) {
            searchCriteria.gameId = parseInt(gameInput);
        }
        if (dateInput) {
            searchCriteria.date = new Date(dateInput).getTime();
        }
        if (stateInput !== "") {
            searchCriteria.state = stateInput === "true";
        }
        if (playerInput) {
            searchCriteria.player = playerInput;
        }

        const queryString = new URLSearchParams(searchCriteria).toString();
        window.location.href = `#sessions?${queryString}`;
    }

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