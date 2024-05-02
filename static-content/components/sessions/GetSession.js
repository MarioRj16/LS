import {div, h1, h2, a, label} from "../../utils/Elements.js";

export async function GetSession(session, players, host) {
    const renderPlayerLinks = await PlayerLinks();

    async function PlayerLinks() {
        const playerLinks = players.map(player => {
            return a(
                { href: `#players/${player.id}`, class:"h2" },
                `${player.name}`
            );
        });

        const joinedPlayerLinks = playerLinks.reduce((accumulator, currentValue, index) => {
            if (index === 0) {
                return [currentValue];
            } else {
                return [...accumulator, ", ", currentValue];
            }
        }, []);

        return div({ class: "h2 player-list" }, ...joinedPlayerLinks);
    }

    const hostLink = a(
        { href: `#players/${host.id}`, class:"h2" },
        `${host.name}`
    );

    const gameLink = a(
        { href: `#games/${session.game}`, class:"h2" },
        `${session.game}`
    );

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        h1({ class: "card-header text-center" }, `${host.name}'s Session`),
        div(
            { class: "card-body text-center" },
            div(
                { class: "session-details" },
                label({class:"h2"}, "Game: "),
                gameLink
            ),
            h2({}, `Capacity: ${players.length}/${session.capacity}`),
            h2({}, `Starting Date: ${session.date}`),
            div(
                { class: "session-details" },
                label({class:"h2"}, `Host: `),
                hostLink,
            ),
            div(
                { class: "session-details" },
                label({class:"h2"}, `Participants: `),
                renderPlayerLinks
            ),
        )
    );
}
