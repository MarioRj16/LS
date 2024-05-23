import {div, h1} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {objectToQueryString} from "../../utils/FetchAPI.js";
import {Paginate} from "../../components/Paginate.js";
import {CardSession} from "../../components/sessions/CardSession.js";
import {AllCards} from "../../components/AllCards.js";

export async function SessionsPage(state) {
    const sessions = (await FetchAPI(`/sessions${objectToQueryString(state.query)}`));
    const cards = sessions.sessions.map(session => CardSession(session));
    const paginate = Paginate(state.query,"sessions",sessions.hasPrevious,sessions.hasNext)

    return AllCards(cards,paginate,"Sessions");
}
