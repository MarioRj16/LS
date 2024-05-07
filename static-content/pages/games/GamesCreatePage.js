import {div, h1, input, button, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions, GenresOptionsInputs} from "../../components/GenresOptions.js";
import {CreateGame} from "../../components/games/CreateGame.js";

export async function GamesCreatePage(state) {
    const genres = (await FetchAPI(`/genres`)).genres;

    const genresOptions = await GenresOptions(genres);

    return CreateGame(genresOptions);
}
