import {a, button, div, h1, h2, label} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";

export async function GetSession(session, players, host , user) {
    const renderPlayerLinks = await PlayerLinks();
    console.log(session)
    async function PlayerLinks() {
        const playerLinks = players.map(player => {
            return a(
                    {href: `#players/${player.id}`, class: "h2"},
                    `${player.name}`
                )
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
        { href: `#games/${session.game.id}`, class:"h2" },
        `${session.game.name}`
    );

    const hostButtons = await getHostButtons()
    const playerButtons = await getPlayerButtons()
    async function getHostButtons(){
        if (user === host.id){
            const updateButton = button({ class: "btn btn-primary ", type: "submit" }, "Update Session");
            (await updateButton).addEventListener('click', updateSession);
            return updateButton
        }else return div()
    }

    async function updateSession(){
        window.location.href=`#sessions/${session.id}/update`
    }

    async function leaveSession(){
        const request= await FetchAPI(`/sessions/${session.id}/players/${user}`,`DELETE`)
        if(request.message==="Cannot remove player from closed session"){
            alert("Cannot remove player from closed session")
        } else{
            alert("Left Session Successfully")
            window.location.reload()
        }
    }

    async function joinSession(){
        await FetchAPI(`/sessions/${session.id}`,`POST`)
        alert("Joined Session Successfully")
        window.location.reload()
    }

    async function getPlayerButtons(){
        if(players.find(player => player.id === user)){
            const leaveButton = button({ class: "btn btn-primary ", type: "submit" }, "Leave Session");
            (await leaveButton).addEventListener('click', leaveSession);
            return div(
                {class: "card-footer Text-center "},
                leaveButton
            )
        }else if(session.isOpen === true){
            const joinButton = button({ class: "btn btn-primary ", type: "submit" }, "Join Session");
            (await joinButton).addEventListener('click', joinSession);
            return div(
                {class: "card-footer Text-center"},
                joinButton
            )
        } else return div()
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
            hostButtons,
            playerButtons
        )
    );
}
