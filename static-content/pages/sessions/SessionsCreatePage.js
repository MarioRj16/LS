import { button, div, h1, input, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {OptionsGames} from "../../components/games/OptionsGames.js";
import {CreateSession} from "../../components/sessions/CreateSession.js";

export async function SessionsCreatePage(state) {
    const games=await OptionsGames()

    async function handleFormSubmit() {
        event.preventDefault();
        let gameInput = document.getElementById('gameInput').value;
        try{
            gameInput=document.getElementById(gameInput).accessKey;
        }catch(error){
            gameInput=null
        }
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

    return CreateSession(games,handleFormSubmit);
}
