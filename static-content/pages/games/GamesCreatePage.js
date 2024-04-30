import {div, h1, input, select, option, button, form, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions } from "../../components/GenresOptions.js";

export async function GamesCreatePage(state) {
    const genres = await FetchAPI(`/games/genres`);

    const formSubmitHandler = async (event) => {
        event.preventDefault();

        const nameInput = document.getElementById('nameInput').value;

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

        if (nameInput.trim() !== "") {
            searchCriteria.name = nameInput.trim();
        }

        if (developerInput.trim() !== "") {
            searchCriteria.developer = developerInput.trim();
        }

        if (genreInput.length > 0) {
            searchCriteria.genres = genreInput.join(',');
        }

        const queryString = new URLSearchParams(searchCriteria).toString();

        window.location.href = `#games?${queryString}`;
    };



    const genresOptions = await GenresOptions(genres);

    const searchButton = button({class: "btn btn-primary", type: "button"}, "Search"); // Create search button with onclick event
    (await searchButton).addEventListener('click', formSubmitHandler);

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Search Games")
        ),
        div(
            { class: "card-body" },
            form(
                {
                    class: "games-search-container d-flex flex-column gap-4",
                    onsubmit: formSubmitHandler
                },
                div(
                    {},
                    label({ class: "form-label", for: "nameInput" }, "Name"),
                    input({ type: "text", class: "form-control", id: "nameInput", placeholder: "(optional)" })
                ),

                div(
                    {},
                    label({ class: "form-label", for: "developerInput" }, "Developer"),
                    input({ type: "text", class: "form-control", id: "developerInput", placeholder: "(optional)" })
                ),
                div(
                    {},
                    label({ class: "form-label", for: "genreInput" }, "Genres"),
                    genresOptions
                ),
                div(
                    { class: "mx-auto" },
                    searchButton
                )
            )
        )
    );


}
