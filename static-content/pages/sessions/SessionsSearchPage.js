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

        if (gameInput) {
            searchCriteria.gameId = parseInt(gameInput);
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

     const games=await GamesOptions((await FetchAPI(`/games`)).games)

    async function GamesOptions(games) {

        if (!Array.isArray(games)) {
            console.error('Games must be provided as an array.');
            return null;
        }

        const selectElement = document.createElement('select');
        selectElement.id = "gameInput";
        selectElement.classList.add("form-control"); // Add CSS class for styling
        const defaultOption = document.createElement('option');
        defaultOption.textContent = "Select a game";
        //defaultOption.disabled = true;
        defaultOption.selected = true;
        selectElement.appendChild(defaultOption);

        if (games.length > 0) {
            games.forEach(game => {
                const optionElement = document.createElement('option');
                optionElement.value = game.id;
                optionElement.textContent = game.name;
                selectElement.appendChild(optionElement);
            });
        }

        return selectElement;
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
