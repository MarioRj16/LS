import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetSession} from "../../components/sessions/GetSession.js";

export async function SessionsDetailsPage(state){
    const id = state.params.id;
    const session = await FetchAPI(`/sessions/${id}`)

    const playerPromises = session.players.map(player => FetchAPI(`/player/${player.id}`));

    const players = await Promise.all(playerPromises);

    const host = await FetchAPI(`/player/${session.host}`);

    return GetSession(session, players, host);
}