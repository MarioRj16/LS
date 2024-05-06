import {button, div, form, h1, input, label, select} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";

export async function SessionsCreatePage(state) {
    async function handleFormSubmit(event) {
        event.preventDefault();

        const gameInput = document.getElementById('gameInput').value;
        const dateInput = document.getElementById('dateInput').value;
        const capacityInput = document.getElementById('capacityInput').value;

        const enteredDate = new Date(dateInput);
        const earliestDate = new Date("1970-01-01T00:00:00");
        if (enteredDate < earliestDate) {
            alert("Please enter a date and time after January 1, 1970.");
            return;
        }
        if (!gameInput || gameInput === "Select a game") {
            alert("Please select a game.");
            return;
        }
        if (!dateInput) {
            alert("Please enter a date and time.");
            return;
        }
        const capacity = parseInt(capacityInput);
        if (isNaN(capacity) || capacity <= 0 || !Number.isInteger(capacity)) {
            alert("Please enter a valid positive integer for capacity.");
            return;
        }

        const params = {
            gameId: parseInt(gameInput),
            startingDate: new Date(dateInput).getTime(),
            capacity: capacity
        };
        console.log(params)
        const create = await FetchAPI(`/sessions`, 'POST', params)
        console.log(create)

    }

    const submitButton = await button({ class: "btn btn-primary", type: "submit" }, "Create");
    submitButton.addEventListener('click', handleFormSubmit);

    const games = (await FetchAPI(`/games`)).games;

    const selectElement = document.createElement('select');
    selectElement.id = "gameInput";
    selectElement.classList.add("form-control");
    const defaultOption = document.createElement('option');
    defaultOption.textContent = "Select a game";
    defaultOption.selected = true;
    selectElement.appendChild(defaultOption);

    games.forEach(game => {
        const optionElement = document.createElement('option');
        optionElement.value = game.id;
        optionElement.textContent = game.name;
        selectElement.appendChild(optionElement);
    });

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Create a Session")
        ),
        div(
            { class: "card-body" },
            form(
                {
                    class: "sessions-create-container d-flex flex-column gap-4",
                    onsubmit: handleFormSubmit
                },
                div(
                    {},
                    label({ class: "form-label", for: "gameInput" }, "Game"),
                    selectElement
                ),
                div(
                    {},
                    label({ class: "form-label", for: "dateInput" }, "Date and Time"),
                    input({ class: "form-control", type: "datetime-local", id: "dateInput", required: true })
                ),
                div(
                    {},
                    label({ class: "form-label", for: "capacityInput" }, "Capacity"),
                    input({ class: "form-control", type: "number", placeholder:"(required)",id: "capacityInput", min: 1, required: true })
                ),
                div(
                    { class: "mx-auto" },
                    submitButton
                )
            )
        )
    );
}
