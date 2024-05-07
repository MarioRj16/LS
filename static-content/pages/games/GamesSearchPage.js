import {div, h1, input, button, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions, GenresOptionsInputs } from "../../components/GenresOptions.js";
import {SearchGames} from "../../components/games/SearchGames.js";

export async function GamesSearchPage(state) {
    const genres = (await FetchAPI(`/genres`)).genres;

    const genresOptions = await GenresOptions(genres);

   return SearchGames(genresOptions);
}
