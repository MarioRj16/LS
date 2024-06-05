import {div, h1, input, button, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import { GenresOptions, GenresOptionsInputs} from "../../components/GenresOptions.js";
import {CreateGame} from "../../components/games/CreateGame.js";

export async function GamesCreatePage(state) {
    const genres = (await FetchAPI(`/genres`)).genres;

    const genresOptions = await GenresOptions(genres);

    async function formSubmitHandler () {
        event.preventDefault();

        const nameInput = document.getElementById('nameInput').value;
        const developerInput = document.getElementById('developerInput').value;
        const genreInput = GenresOptionsInputs();

        if (nameInput.trim() === "") {
            alert("Please enter a name");
            return;
        }

        if (developerInput.trim() === "") {
            alert("Please enter a developer");
            return;
        }

        if (genreInput.length <= 0) {
            alert("Please select at least one genre");
            return;
        }
        const params={
            name : nameInput.trim(),
            developer : developerInput.trim(),
            genres : genreInput
        }
        const create = await FetchAPI(`/games`, 'POST', params)
        if(create.id)alert("Game created successfully");
        else alert(`${create.message}`);
        window.location.reload()
    };

    return CreateGame(genresOptions,formSubmitHandler);
}
