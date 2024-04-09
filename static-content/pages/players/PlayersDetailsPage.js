import {FetchAPI} from "../../utils/FetchAPI";
import {GetPlayer} from "../../components/players/GetPlayer";

export async function PlayersDetailsPage(state){
    const id = state.params.id;
    const player = await FetchAPI(`/players/${id}`)
    return GetPlayer(player)
}