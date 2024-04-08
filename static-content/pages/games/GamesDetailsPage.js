import {FetchAPI} from "../../utils/FetchAPI";
import {GetPlayer} from "../../components/players/GetPlayer";
import {GetGame} from "../../components/games/GetGame";

export async function GamesDetailsPage(state){
    const id = state.params.id;
    const game = await FetchAPI(`/games/${id}`)
    return GetGame(game)
}