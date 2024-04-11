import { div, h1, input, select, option, button } from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js"; // Import necessary elements from your utility file

export async function GamesSearchPage(state) {

    const genres= await FetchAPI(`/genres`)
    const handleSearch = async () => {
        const developerInput = document.getElementById('developerInput').value;
        const genreInput = document.getElementById('genreInput').value.split(',').map(genre => genre.trim());


        const searchCriteria = {
            developer: developerInput || null,
            genres: genreInput.length > 0 ? genreInput : null,
        };
        window.location.href = `#games?${new URLSearchParams(searchCriteria).toString()}`;
    };

    return div(
        { class: "game-search-container" },
        h1({}, "Search Games"),
        div(
            { class: "search-inputs" },
            input({ type: "text", id: "developerInput", placeholder: "Developer (optional)" }),
            div(
                { class: "select-input" },
                select({ id: "genreInput", multiple: true, placeholder: "Select genres (optional)" },
                    genres.map(genre => (
                        option({ value: genre.id, key: genre.id }, genre.name)
                    )))
            ),
            button({ onclick: handleSearch }, "Search")
        ),
    );
}
