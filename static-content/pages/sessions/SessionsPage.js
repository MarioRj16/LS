import {div, h1, a, h2, h3, button, form} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {objectToQueryString} from "../../utils/FetchAPI.js";
import {Paginate} from "../../components/Paginate.js";
import {CardSession} from "../../components/sessions/CardSession.js";
import {AllCards} from "../../components/AllCards.js";

export async function SessionsPage(state) {
    const sessionsResponse = await FetchAPI(`/sessions${objectToQueryString(state.query)}`);
    const sessions= sessionsResponse.sessions;

    const cards = sessions.map(session => CardSession(session));

    const paginatedCards = await AllCards(cards);

    const paginate = Paginate(state.query)

    return div(
        {class:"card"},
        h1({ class: "card-header d-flex justify-content-center"}, `Sessions`),
        div(
            { class: "session-list" },
            paginatedCards
        ),
        paginate
    );

}
