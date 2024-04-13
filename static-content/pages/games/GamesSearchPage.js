import { div, h1, input, select, option, button } from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions } from "../../components/GenresOptions.js";

export async function GamesSearchPage(state) {
    const genres = await FetchAPI(`/genres`);

    const formSubmitHandler = async () => {
        event.preventDefault()

        const developerInput = document.getElementById('developerInput').value;
        const genreInput = document.getElementById('genreInput').value.split(',').map(genre => genre.trim());

        const searchCriteria = {};

        // Add non-null values to the search criteria
        if (developerInput) {
            searchCriteria.developer = developerInput;
        }
        if (genreInput.length > 0) {
            searchCriteria.genres = genreInput;
        }

        const queryString = new URLSearchParams(searchCriteria).toString();
        window.location.href = `#games?${queryString}`;
    };

    const genresOptions = await GenresOptions(genres);

    const searchButton = button({ type: "button"}, "Search"); // Create search button with onclick event
    (await searchButton).addEventListener('click', formSubmitHandler);




    return div(
        { class: "game-search-container" },
        h1({}, "Search Games"),
        div(
            { class: "search-form" },
            form(
                { onsubmit: formSubmitHandler },
                input({ type: "text", id: "developerInput", placeholder: "Developer (optional)" }),
                select({ id: "genreInput" }, genresOptions),
                searchButton
            )
        )
    );
}
