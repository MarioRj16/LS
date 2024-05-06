import {a, div} from "../../utils/Elements.js";

export async function ShowPlayers(session,listener,color){
    const playerLinks = session.players.map(async player => {
        const playerButton = a({ class: `h2 ${color}`, type: "submit" }, player.name);
        (await playerButton).addEventListener('click',() => listener(player.id));
        return playerButton
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