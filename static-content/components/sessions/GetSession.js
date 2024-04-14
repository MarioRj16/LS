import {button, div, h1, h2, h3} from "../../utils/Elements.js";


export async function GetSession(session,players,host){

    const renderPlayerHeaders= PlayerHeaders()
    async function PlayerHeaders (){

        players.map => {
            const detailsButton = button(
                {class: "btn btn-primary", type: "button"},
                "Details"
            );

            (await detailsButton).addEventListener('click', handleClick(player.id))
            return div(
                h3({}, `Player: ${player.name}`),
                detailsButton
            );
        });
    }}
    /*async function handleClick(id){
        event.preventDefault();
        window.location.href = `#sessions?playerId=${id}`;
    }

     */
    function handleClick(playerId) {
        return () => {
            window.location.href = `#playerId/${playerId}`;
        };
    }




    return div(
        {class: "row justify-content-evenly"},
        h1({class:""},`Session`),
        div(
            h2({}, `Game: ${session.game}`),
            h2({}, `Capacity: ${session.capacity}`),
            h2({}, `StartingDate: ${session.date}`),
            h2({},`Creator: ${host.name}`),

            h2({},`Participants:`),
            div(
                renderPlayerHeaders()
            )
        )
    )
}