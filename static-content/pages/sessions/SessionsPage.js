import {div, h1, a, h2, h3, button, form} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";

export async function SessionsPage(state) {

    const sessionsResponse = await FetchAPI(`/sessions?${state.query}`);
    console.log(sessionsResponse)
    const sessions= sessionsResponse.sessions;

    function handleClick(sessionId) {
        return () => {
            window.location.href = `#sessions/${sessionId}`;
        };
    }

        if (!Array.isArray(sessions) || sessions.length === 0) {
            return div(h1({}, "No sessions found."));
        }

    async function paginate(cards, skip = 0, limit = 10) {
        const paginatedCards = cards.slice(skip, skip + limit);
        return div(...paginatedCards);
    }

    const cards = sessions.map(session => sessionCard(session));

    const paginatedCards = await paginate(cards);

        async function sessionCard(session){
            const detailsButton = button(
                { class: "btn btn-primary", type: "button" },
                "Details"
            );

            (await detailsButton).addEventListener('click', handleClick(session.id))

            return form(
                div(
                { class: "session-item" },
                    h2({}, `Session ID: ${session.id}`),
                    h3({}, `Game: ${session.game}`),
                    h3({}, `Starting Date: ${session.startingDate}`),
                    detailsButton
            ))
        }

        return div(
            h1({}, `Sessions`),
            div({ class: "session-list" }, sessionList)
        );

}
