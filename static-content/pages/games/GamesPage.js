import { div, h1, a, h2, h3, button, form, label, input } from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {objectToQueryString} from "../../utils/FetchAPI.js";

export async function GamesPage(state) {

    const gamesResponse = await FetchAPI(`/games${objectToQueryString(state.query)}`);
    const games = gamesResponse.games;
    function handleClick(gameId) {
        return () => {
            window.location.href = `#games/${gameId}`;
        };
    }

    async function gameCard(game) {
        const detailsButton = button(
            { class: "btn btn-primary", type: "button" },
            "Details"
        );

        (await detailsButton).addEventListener('click', handleClick(game.id))
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


    async function paginate(cards) {
        return div({class:"card-container"},
            ...cards)
            ;
    }

    const cards = games.map(game => gameCard(game));

    const paginatedCards = await paginate(cards);

    if(!Array.isArray(games) || games.length === 0){
        return div(h1({class:"text-center"}, "No games found"));
    }

    return div(
        h1({ class: "d-flex justify-content-center" }, "Games"),
        div(
            { class: "game-list" },
            paginatedCards
        ),
        div(
            //TODO(add next and previous buttons)
        )
    );
}
