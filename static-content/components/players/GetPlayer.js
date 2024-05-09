import {button, div, h1, h2} from "../../utils/Elements.js";

export async function GetPlayer(player){

    const hostButton = button({ class: "btn btn-primary ", type: "submit" }, "Hosted Sessions");
    (await hostButton).addEventListener('click', SearchSessionsHost);

    const playerButton = button({ class: "btn btn-primary ", type: "submit" }, "Participant Sessions");
    (await playerButton).addEventListener('click', SearchSessionsParticipations);

    async function SearchSessionsParticipations(){
        window.location.href = `#sessions?player=${player.name}`;
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
                div(
                    div(
                        { class: "text-center" },
                        h2({}, `Email: ${player.email}`),
                        h2({}, `Name: ${player.name}`)
                    )
                ),
                div(
                    {class: "d-flex justify-content-between gap-4 "},
                    hostButton,
                    playerButton
                )
            )
        )
    )
}