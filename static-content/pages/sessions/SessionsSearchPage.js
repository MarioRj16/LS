import {button, datalist, div, form, h1, input, label, option, select} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";
import {createElement} from "../../utils/Utils.js";

export async function SessionsSearchPage(state) {
    function handleFormSubmit(event) {
        event.preventDefault(); // Prevent default form submission behavior
        const gameInput = document.getElementById(
            document.getElementById('gameInput').value).accessKey;
        const dateInput = document.getElementById('dateInput').value;
        const stateInput = document.getElementById('stateInput').value;
        const playerEmailInput = document.getElementById('playerEmailInput').value;

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
        if (playerEmailInput) {
            searchCriteria.playerEmail = playerEmailInput;
        }


        const queryString = new URLSearchParams(searchCriteria).toString();
        window.location.href = `#sessions?${queryString}`;
    }

    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Search");
     (await submitButton).addEventListener('click', handleFormSubmit);

     const games=await GamesOptions()

    async function GamesOptions() {
        const dataList= await datalist({ id: "games" })
        const textInput = await input({
            type: "text",
            class: "form-control",
            id: "gameInput",
            placeholder: "Game Name",
            list: "games"
        });

        textInput.addEventListener('input', async () => {
            await updateOptions(dataList)
        });
        return div({},textInput,dataList)
    }


    async function updateOptions(dataList){
        const input= document.getElementById('gameInput').value;
        if(input.length < 3) return;
        FetchAPI(`/games?name=${input}`).then(obj => {
            const games = obj.games
            //try to erase this by children
            dataList.innerHTML = '';
            for (const game of games) {
                createElement("option",({value: game.name, id: game.name, accessKey: game.id}))
                    .then(option => dataList.appendChild(option))
            }
        })
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
                    //input({ type: "text", class: "form-control", id: "game", placeholder: "Game Name", list: "games"}),
                    games
                ),
                div(
                    {},
                    label({ class: "form-label", for: "dateInput" }, "Date and time"),
                    input({ class: "form-control", type: "datetime-local", id: "dateInput", placeholder: "Date and time" })
                ),
                div(
                    {},
                    label({ class: "form-label", for: "playerEmail" }, "Player Email"),
                    input({ class: "form-control", id: "playerEmailInput", placeholder: "(optional)" })
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
