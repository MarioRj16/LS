import {div, h1, a, h2, h3} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";

export async function SessionsPage(state) {

    const sessions = await FetchAPI(`/sessions?${state.query}`); // Assuming FetchAPI is a function that fetches data from an API

    // Function to handle session click (redirect to session details)
    const handleSessionClick = (sessionId) => {
        // Redirect to session details page using sessionId (replace with your actual redirect logic)
        console.log('Redirecting to session details:', sessionId);
        window.location.href = `#sessions/${sessionId}`;
    };

    return div(
        h1({ class: "" }, `Sessions`),
        div(
            { class: "session-list" },
            sessions.map(session => (
                a(
                    {
                        onclick: () => handleSessionClick(session.id),
                        key: session.id
                    },
                    div(
                        { class: "session-item" },
                        h2({}, `Session ID: ${session.id}`),
                        h3({}, `Game: ${session.game}`),
                        h3({}, `Starting Date: ${session.startingDate}`)
                    )
                )
            ))
        )
    );
}
