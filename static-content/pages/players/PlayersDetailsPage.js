import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetPlayer} from "../../components/players/GetPlayer.js";
import {button, div, h1, h2} from "../../utils/Elements.js";

export async function PlayersDetailsPage(state){
    const id = state.params.id;

    const player = await FetchAPI(`/players/${id}`)

    return GetPlayer(player)
}