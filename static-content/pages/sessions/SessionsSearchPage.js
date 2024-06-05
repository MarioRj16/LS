import {button, div, h1, input, label, option, select} from "../../utils/Elements.js";
import {OptionsGames} from "../../components/games/OptionsGames.js";
import {SearchSessions} from "../../components/sessions/SearchSessions.js";
import {OptionsPlayers} from "../../components/players/OptionsPlayers.js";

export async function SessionsSearchPage(state) {
    const games= await OptionsGames()

    const players = await OptionsPlayers()

    function handleFormSubmit() {
        event.preventDefault();

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
            searchCriteria.playerName = playerInput;
        }

        const queryString = new URLSearchParams(searchCriteria).toString();
        window.location.href = `#sessions?${queryString}`;
    }

    return SearchSessions(games,players,handleFormSubmit)
}
