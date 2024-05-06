import { button, div, h1, input, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {OptionsGames} from "../../components/games/OptionsGames.js";

export async function SessionsCreatePage(state) {

    const submitButton = await button({ class: "btn btn-primary", type: "submit" }, "Create");
    submitButton.addEventListener('click', handleFormSubmit);

    const games=await OptionsGames()

    async function handleFormSubmit(event) {
        event.preventDefault();
        let gameInput = document.getElementById('gameInput').value;
        if (gameInput!=null && gameInput!=="") gameInput=document.getElementById(gameInput).accessKey;
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
        const create = await FetchAPI(`/sessions`, 'POST', params)
        if(create.id)alert("Session created successfully");
        else alert(`${create.message}`);
        window.location.reload()
    }

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        h1({class: "card-header"}, "Create a Session"),
        div(
        { class: "card-body d-flex flex-column gap-4" },
            div(
                {},
                label({ class: "form-label", for: "gameInput" }, "Game"),
                games
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
    );
}
