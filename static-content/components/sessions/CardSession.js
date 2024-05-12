import {button, div, h3} from "../../utils/Elements.js";

export async function CardSession(session){
    const detailsButton = button(
        { class: "btn btn-primary", type: "button" },
        "Details"
    );

    (await detailsButton).addEventListener('click', handleClick(session.id))

    return div(
        {class: "card mx-auto justify-content-center w-50 maxH-50 m-2"},
        div(
            { class: "session-details" },
            h3({class:"card-header  text-center"}, `${session.game.name} Session`),
            div(
                { class: "card-body text-center"},
                h3({},`StartDate: ${session.date}`),
                h3({},`Capacity: ${session.currentCapacity}/${session.capacity}`),
                detailsButton
            )
        ))
}

function handleClick(sessionId) {
    return () => {
        window.location.href = `#sessions/${sessionId}`;
    };
}
