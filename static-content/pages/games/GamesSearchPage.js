import {div, h1, input, select, option, button, form, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions } from "../../components/GenresOptions.js";

export async function GamesSearchPage(state) {
    const genres = await FetchAPI(`/games/genres`);

    const formSubmitHandler = async (event) => {
        event.preventDefault();

        const developerInput = document.getElementById('developerInput').value;

        const genreInput = [];
        const checkboxes = document.querySelectorAll('input[type="checkbox"][id^="genre_"]');
        checkboxes.forEach(checkbox => {
            if (checkbox.checked) {
                const genreId = checkbox.value;
                genreInput.push(genreId);
            }
        });

        const searchCriteria = {};

        // Add developer input to search criteria if provided
        if (developerInput.trim() !== "") {
            searchCriteria.developer = developerInput.trim();
        }

        // Add selected genre IDs to search criteria as a single 'genres' parameter
        if (genreInput.length > 0) {
            searchCriteria.genres = genreInput.join(',');
        }

        // Construct the query string using URLSearchParams
        const queryString = new URLSearchParams(searchCriteria).toString();

        // Navigate to the games page with the constructed query string appended to the URL
        window.location.href = `#games?${queryString}`;
    };



    const genresOptions = await GenresOptions(genres);

    const searchButton = button({ type: "button"}, "Search"); // Create search button with onclick event
    (await searchButton).addEventListener('click', formSubmitHandler);

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header text-center" },
            h1({}, "Search Games")
        ),
        div(
            { class: "card-body d-flex flex-column align-items-center" },
            form(
                {
                    class: "sessions-search-container d-flex flex-column gap-4",
                    onsubmit: formSubmitHandler
                },
                div(
                    { class: "text-center" },
                    label({ class: "form-label", for: "developerInput" }, "Developer"),
                    input({ type: "text", class: "form-control text-center", id: "developerInput", placeholder: "(optional)" })
                ),
                div(
                    { class: "text-center" },
                    label({ class: "form-label", for: "genreInput" }, "Genres"),
                    genresOptions
                ),
                div(
                    { class: "text-center" },
                    searchButton
                )
            )
        )
    );


}
