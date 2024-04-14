import {div, h1, input, select, option, button, form, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions } from "../../components/GenresOptions.js";

export async function GamesSearchPage(state) {
    const genres = await FetchAPI(`/games/genres`);
    console.log("Genres: ", genres)

    const formSubmitHandler = async () => {
        event.preventDefault()

        const developerInput = document.getElementById('developerInput').value;
        const genreInput = document.getElementById('genreInput').value.split(',').map(genre => genre.trim());

        const searchCriteria = {};

        // Add non-null values to the search criteria
        if (developerInput) {
            searchCriteria.developer = developerInput;
        }
        if (genreInput.length > 0 && genreInput[0] !== "") {
            console.log(genreInput)
            searchCriteria.genres = genreInput;
        }

        const queryString = new URLSearchParams(searchCriteria).toString();
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
                    onsubmit: formSubmitHandler // Use the function to handle form submission
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
