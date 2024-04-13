import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetGame} from "../../components/games/GetGame.js";

export async function GamesDetailsPage(state){
    const id = state.params.id;
    const game = await FetchAPI(`/games/${id}`)
    return GetGame(game)
}