import {button, div, h3} from "../../utils/Elements.js";

export async function CardGame(game) {
    const detailsButton = button(
        { class: "btn btn-primary", type: "button" },
        "Details"
    );

    function handleClick() {
       window.location.href = `#games/${game.id}`;
    }

    (await detailsButton).addEventListener('click', handleClick)
    return div(
        {class: "card mx-auto justify-content-center w-50 maxH-50 m-2"},
        div(
            { class: "game-details" },
            h3({class: "card-header text-center"}, `${game.name}`),
            div(
                { class: "card-body text-center"},
                h3({class:"justify-content-center"}, `Developer: ${game.developer}`),
                detailsButton
            )

        )
    )
}

