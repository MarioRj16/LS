import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetPlayer} from "../../components/players/GetPlayer.js";
import {button, div, h1, sub} from "../../utils/Elements.js";

export async function PlayersDetailsPage(state){

    //TODO(how are we suposed to search for game session, do we have to give gameid?)
    const id = state.params.id;
    console.log(id)
    const player = await FetchAPI(`/player/${id}`)
    const submitButton = button({ class: "btn btn-primary ", type: "submit" }, "Search");
    (await submitButton).addEventListener('click', SearchSessionsParticipations);

    async function SearchSessionsParticipations(){
        window.location.href = `#sessions?playerId=${id}`;
    }

    return div(
        h1({ class: "d-flex flex-column align align-items-center" }, "Player"),
        div(
        { class: "d-flex flex-column align-items-center" },
        GetPlayer(player),
        submitButton
        )
    )
}