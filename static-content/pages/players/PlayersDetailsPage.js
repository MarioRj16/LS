import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetPlayer} from "../../components/players/GetPlayer.js";
import {button, div, form, h1, hr, spacer, sub} from "../../utils/Elements.js";

export async function PlayersDetailsPage(state){

    //TODO(how are we suposed to search for game session, do we have to give gameid?)
    const id = state.params.id;
    const player = await FetchAPI(`/player/${id}`)
    const hostButton = button({ class: "btn btn-primary ", type: "submit" }, "Hosted Sessions");
    (await hostButton).addEventListener('click', SearchSessionsHost);


    const playerButton = button({ class: "btn btn-primary ", type: "submit" }, "Participant Sessions");
    (await playerButton).addEventListener('click', SearchSessionsParticipations);

    async function SearchSessionsParticipations(){
        window.location.href = `#sessions?player=${player.email}`;
    }

    async function SearchSessionsHost(){
        window.location.href = `#sessions?host=${player.id}`;
    }

    return div(
        { class: "card" },
        div(
            { class: "card-body" },
            h1({ class: "card-title text-center mb-4" }, `${player.name}'s details`),
            div(
            { class: "d-flex flex-column align-items-center" },
            GetPlayer(player),
                div(
                    {class: "d-flex justify-content-between gap-4 "},
                    hostButton,
                    playerButton
                )
            )
        )
    )
}