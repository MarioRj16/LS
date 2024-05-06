import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetPlayer} from "../../components/players/GetPlayer.js";
import {button, div, h1} from "../../utils/Elements.js";

export async function PlayersDetailsPage(state){

    const id = state.params.id;
    const player = await FetchAPI(`/players/${id}`)
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
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        h1({ class: "card-header text-center mb-4" }, `${player.name}'s details`),
        div(
            { class: "card-body" },
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