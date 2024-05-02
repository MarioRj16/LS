import {div, h1, h2, a, label} from "../../utils/Elements.js";

export async function GetSession(session, players, host , user) {
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

    const hostButtons = await getHostButtons()
    const playerButtons = await getPlayerButtons()
    async function getHostButtons(){
        if (user === host.id){
            return div(
                {class: "d-flex justify-content-between gap-4 "},
                hostButton,
            )
        }else return div()
    }

    async function getPlayerButtons(){
        if(players.find(player => player.id === user)){
            return div(
                {class: "d-flex justify-content-between gap-4 "},
                playerButton
            )
        }else return div()
    }

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
