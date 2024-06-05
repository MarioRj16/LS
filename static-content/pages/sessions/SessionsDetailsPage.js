import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetSession} from "../../components/sessions/GetSession.js";
import {getStoredUser} from "../../utils/Utils.js";

export async function SessionsDetailsPage(state){
    const id = state.params.id;

    const session = await FetchAPI(`/sessions/${id}`)

    const host = await FetchAPI(`/players/${session.host}`);

    return GetSession(session, session.players, host, getStoredUser().playerId);
}