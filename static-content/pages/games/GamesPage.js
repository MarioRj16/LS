import { div, h1, a, h2, h3, button, form, label, input } from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";

export async function GamesPage(state) {

    const gamesResponse = await FetchAPI(`/games?${state.query}`);
    const games = gamesResponse.games;
    function handleClick(gameId) {
        return () => {
            console.log(gameId);
            window.location.href = `#games/${gameId}`;
        };
    }

    async function gameCard(game) {
        const detailsButton = button(
            { class: "btn btn-primary", type: "button" },
            "Details"
        );

        (await detailsButton).addEventListener('click', handleClick(game.id))
        return form(
            { class: "game-form d-flex flex-column gap-4", onsubmit: handleClick(game.id) },
            div(
                { class: "game-details" },
                h2({}, `Game ID: ${game.id}`),
                h3({}, `Name: ${game.name}`),
                detailsButton
            )
        );
    }


    async function paginate(cards, skip = 0, limit = 10) {
        const paginatedCards = cards.slice(skip, skip + limit);
        return div(...paginatedCards);
    }

    const cards = games.map(game => gameCard(game));

    const paginatedCards = await paginate(cards);

    return div(
        h1({ class: "" }, "Games"),
        div(
            { class: "game-list" },
            paginatedCards
        ),
        div(
            //TODO(add next and previous buttons)
        )
    );
}
