import {div, h1, a, h2, h3, button, form} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {objectToQueryString} from "../../utils/FetchAPI.js";

export async function SessionsPage(state) {
    const sessionsResponse = await FetchAPI(`/sessions${objectToQueryString(state.query)}`);
    const sessions= sessionsResponse.sessions;

    function handleClick(sessionId) {
        return () => {
            window.location.href = `#sessions/${sessionId}`;
        };
    }

    if (!Array.isArray(sessions) || sessions.length === 0) {
        return div(h1({}, "No sessions found."));
    }

    async function paginate(cards) {
        return div({class:"card-container"},
            ...cards)
            ;
    }

    const cards = sessions.map(session => sessionCard(session));

    const paginatedCards = await paginate(cards);

    async function sessionCard(session){
        const detailsButton = button(
            { class: "btn btn-primary", type: "button" },
            "Details"
        );

        (await detailsButton).addEventListener('click', handleClick(session.id))

        return div(
            {class: "card mx-auto justify-content-center w-50 maxH-50 m-2"},
            div(
                { class: "session-details" },
                h3({class:"card-header  text-center"}, `${session.game} Session`),
                div(
                    { class: "card-body text-center"},
                    h3({},`StartDate: ${session.date}`),
                    h3({},`Capacity: ${session.players}/${session.capacity}`),
                    detailsButton
                )
            ))
        }

        return div(
            h1({ class: "d-flex justify-content-center"}, `Sessions`),
            div(
                { class: "session-list" },
                paginatedCards
            ),
            div(
                //TODO(add next and previous buttons)
            )
        );

}
