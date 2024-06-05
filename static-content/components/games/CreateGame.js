import {GenresOptionsInputs} from "../GenresOptions.js";
import {FetchAPI} from "../../utils/FetchAPI.js";
import {button, div, h1, input, label} from "../../utils/Elements.js";

export async function CreateGame(genresOptions,formSubmitHandler){

    const createButton = button({class: "btn btn-primary", type: "button"}, "Create");
    (await createButton).addEventListener('click', formSubmitHandler);

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        h1({class: "card-header"}, "Create a Game"),
        div(
            { class: "card-body d-flex flex-column gap-4" },
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
    );
}