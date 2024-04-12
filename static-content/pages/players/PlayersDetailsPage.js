import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetPlayer} from "../../components/players/GetPlayer.js";

export async function PlayersDetailsPage(state){
    const id = state.params.id;
    console.log(id)
    const player = await FetchAPI(`/players/${id}`)
    console.log(player)
    return GetPlayer(player)
}