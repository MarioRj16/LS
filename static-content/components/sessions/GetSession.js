import {div, h1, h2, h3} from "../../utils/Elements";


export async function GetSession(session,players,creator){
    //TODO(add on click to redirect to player details)
    const renderPlayerHeaders = () => {
        return players.map((player) => {
            return h3({}, `Player: ${player.name}`);
        });
    };


    return div(
        {class: "row justify-content-evenly"},
        h1({class:""},`Session`),
        div(
            h2({}, `Game: ${session.game}`),
            h2({}, `Capacity: ${session.capacity}`),
            h2({}, `StartingDate: ${session.startingDate}`),
            h2({},`Creator: ${creator}`),
            h2({},`Participants:`),
            div(
                renderPlayerHeaders()
            )
        )
    )
}