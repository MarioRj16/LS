import { div, h1, a, h2, h3 } from "../../utils/Elements";
import { FetchAPI } from "../../utils/FetchAPI";

export async function GamesPage(state) {

    const games = await FetchAPI(`/games?${state.query}`);

    const handleGameClick = (gameId) => {

        console.log('Redirecting to game details:', gameId);
        window.location.href = `#games/${gameId}`;
    };

    return div(
        h1({ class: "" }, `Games`),
        div(
            { class: "game-list" },
            games.map(game => (
                a(
                    {
                        onclick: () => handleGameClick(game.id),
                        key: game.id
                    },
                    div(
                        { class: "game-item" },
                        h2({}, `Game ID: ${game.id}`),
                        h3({}, `Title: ${game.title}`),
                        h3({}, `Developer: ${game.developer || 'Unknown'}`),
                        h3({}, `Genres: ${game.genres.join(', ') || 'N/A'}`)
                    )
                )
            ))
        )
    );
}
