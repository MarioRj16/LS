import { button, div, h1, h2, h3, h4, h5, label } from "../../utils/Elements.js";

export async function GetGame(game) {
    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Search");
    (await submitButton).addEventListener('click', searchGameSession);

    function searchGameSession() {
        window.location.href = `#sessions?gameId=${game.id}`;
    }

    const renderGenres = () => {
        const genresList = game.genres.map(genre => genre.name).join(", ");
        return h2({}, genresList);
    };

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        h1({ class: "card-header text-center " }, game.name),
        div(
            { class: "card-body text-center" },
            h2({}, `Developer: ${game.developer}`),
            label({ class: "h2" }, "Genres: "),
            renderGenres(),
            div(submitButton)
        )
    );
}
