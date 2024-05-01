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
        console.log(params)
        const create = await FetchAPI(`/games`, 'POST', params)
        console.log(create)
    };

    const genresOptions = await GenresOptions(genres);

    const createButton = button({class: "btn btn-primary", type: "button"}, "Create"); // Create search button with onclick event
    (await createButton).addEventListener('click', formSubmitHandler);

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Create a Game")
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
                    input({ type: "text", class: "form-control", id: "nameInput", placeholder: "name (must be unique)" })
                ),

                div(
                    {},
                    label({ class: "form-label", for: "developerInput" }, "Developer"),
                    input({ type: "text", class: "form-control", id: "developerInput", placeholder: "developer" })
                ),
                div(
                    {},
                    label({ class: "form-label", for: "genreInput" }, "Genres"),
                    genresOptions
                ),
                div(
                    { class: "mx-auto" },
                    createButton
                )
            )
        )
    );
}
