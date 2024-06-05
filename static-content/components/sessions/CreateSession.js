import {button, div, h1, input, label} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";

export async function CreateSession(games,handleFormSubmit){

    const submitButton = await button({ class: "btn btn-primary", type: "submit" }, "Create");
    submitButton.addEventListener('click', handleFormSubmit);

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        h1({class: "card-header"}, "Create a Session"),
        div(
            { class: "card-body d-flex flex-column gap-4" },
            div(
                {},
                label({ class: "form-label", for: "gameInput" }, "Game"),
                games
            ),
            div(
                {},
                label({ class: "form-label", for: "dateInput" }, "Date and Time"),
                input({ class: "form-control", type: "datetime-local", id: "dateInput", required: true })
            ),
            div(
                {},
                label({ class: "form-label", for: "capacityInput" }, "Capacity"),
                input({ class: "form-control", type: "number", placeholder:"(required)",id: "capacityInput", min: 1, required: true })
            ),
            div(
                { class: "mx-auto" },
                submitButton
            )
        )
    );

}