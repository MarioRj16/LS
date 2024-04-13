import { div, h1, a, h2, h3 } from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";

export async function SessionsPage(state) {
    try {
        // Fetch sessions data based on the query in the state
        const sessions = await FetchAPI(`/sessions?${state.query}`);

        // Function to handle session click (redirect to session details)
        const handleSessionClick = (sessionId) => {
            console.log('Redirecting to session details:', sessionId);
            // Redirect to session details page using sessionId
            window.location.href = `#sessions/${sessionId}`;
        };

        // Check if sessions is an array and not empty
        if (!Array.isArray(sessions) || sessions.length === 0) {
            return div(h1({}, "No sessions found."));
        }

        // Render the sessions list
        const sessionList = sessions.map(session => {
            const sessionItem = div(
                { class: "session-item" },
                h2({}, `Session ID: ${session.id}`),
                h3({}, `Game: ${session.game}`),
                h3({}, `Starting Date: ${session.startingDate}`)
            );

            // Create an anchor element with an onclick event handler
            const sessionLink = document.createElement('a');
            sessionLink.appendChild(sessionItem);
            sessionLink.onclick = () => handleSessionClick(session.id);

            return sessionLink;
        });

        // Return the overall structure of the sessions page
        return div(
            h1({}, `Sessions`),
            div({ class: "session-list" }, sessionList)
        );
    } catch (error) {
        console.error('Error fetching sessions:', error);
        return div(h1({}, "Error fetching sessions. Please try again later."));
    }
}
