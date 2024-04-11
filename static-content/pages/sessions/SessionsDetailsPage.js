import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetSession} from "../../components/sessions/GetSession.js";

export async function SessionsDetailsPage(state){
    const id = state.params.id;
    const session = await FetchAPI(`/sessions/${id}`)
    const players = await session.players.map((playerId) => FetchAPI(`/players/${playerId}`));
    const creator = await FetchAPI(`/players/${session.creator}`)
    return GetSession(session,players,creator)
}