import {div, h1, input, button, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions } from "../../components/GenresOptions.js";
import {GenresOptionsInputs} from "../../components/GenresOptionsInputs.js";

export async function GamesSearchPage(state) {
    const genres = (await FetchAPI(`/genres`)).genres;

    const genresOptions = await GenresOptions(genres);

    const formSubmitHandler = async (event) => {
        event.preventDefault();

        const nameInput = document.getElementById('nameInput').value;
        const developerInput = document.getElementById('developerInput').value;
        const genreInput = GenresOptionsInputs();

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

    const searchButton = button({class: "mx-auto btn btn-primary", type: "button"}, "Search");
    (await searchButton).addEventListener('click', formSubmitHandler);

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Search Games")
        ),
        div(
        { class: "card-body d-flex flex-column gap-4" },
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
            searchButton
        )
    );
}
