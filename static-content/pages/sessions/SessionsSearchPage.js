import { button, div, form, h1, input, label, option, select } from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";

export async function SessionsSearchPage(state) {
    function handleFormSubmit(event) {
        event.preventDefault(); // Prevent default form submission behavior

        const gameInput = document.getElementById('gameInput').value;
        const dateInput = document.getElementById('dateInput').value;
        const stateInput = document.getElementById('stateInput').value;
        const playerIdInput = document.getElementById('playerIdInput').value;


        const searchCriteria = {};

        // Add non-null values to the search criteria
        if (gameInput) {
            searchCriteria.game = parseInt(gameInput);
        }
        if (dateInput) {
            searchCriteria.date = new Date(dateInput);
        }
        if (stateInput !== "") {
            searchCriteria.state = stateInput === "true";
        }
        if (playerIdInput) {
            searchCriteria.playerId = parseInt(playerIdInput);
        }

        const queryString = new URLSearchParams(searchCriteria).toString();
        window.location.href = `#sessions?${queryString}`;
    }

    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Search");
     (await submitButton).addEventListener('click', handleFormSubmit);

     const games=await GamesOptions(await FetchAPI(`/games`))

    async function GamesOptions(games) {
        // Create a <select> element for games with single selection
        const optionsContainer = select({ id: "gameInput", placeholder: "Select a game" });
        console.log(games.games)
        // Map each game to an <option> element and append to the optionsContainer <select>
        if(games.games.length!==0) {
            games.forEach(game => {
                const optionElement = option({value: game.id}, game.title);
                optionsContainer.appendChild(optionElement);
            });
        }
        // Return the <div> containing the <select> element with options
        return div({ class: "select-input" }, optionsContainer);
    }


    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Sessions Search")
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
                    label({ class: "form-label", for: "gameInput" }, "Game "),
                    //input({ class: "form-control", type: "number", id: "gameInput", placeholder: "Game ID", min: 1, required: true })
                    games
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
                    submitButton
                )
            )
        )
    );
}
