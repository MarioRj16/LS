import {div, h1, input, button, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions, GenresOptionsInputs } from "../../components/GenresOptions.js";
import {SearchGames} from "../../components/games/SearchGames.js";

export async function GamesSearchPage(state) {
    const genres = (await FetchAPI(`/genres`)).genres;

    const genresOptions = await GenresOptions(genres);

    async function formSubmitHandler (){
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

   return SearchGames(genresOptions,formSubmitHandler);
}
