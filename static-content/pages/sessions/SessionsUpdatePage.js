import {FetchAPI} from "../../utils/FetchAPI.js";
import {UpdatePlayer} from "../../components/players/UpdatePlayer.js";

export async function SessionsUpdatePage(state){
    const id = state.params.id;

    const session = await FetchAPI(`/sessions/${id}`)

    return UpdatePlayer(session);
}