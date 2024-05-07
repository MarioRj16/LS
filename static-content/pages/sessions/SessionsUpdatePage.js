import {FetchAPI} from "../../utils/FetchAPI.js";
import {UpdateSession} from "../../components/sessions/UpdateSession.js";

export async function SessionsUpdatePage(state){
    const id = state.params.id;

    const session = await FetchAPI(`/sessions/${id}`)

    return UpdateSession(session);
}