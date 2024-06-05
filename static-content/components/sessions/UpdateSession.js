import {a, button, div, h2, input, label} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";
import {ShowPlayers} from "../players/ShowPlayers.js";

export async function UpdateSession(session,removeFromSession,deleteSession,updateSession){
    const removeFromButton = button({class: "btn btn-primary ", type: "submit"}, "Remove from Session");
    (await removeFromButton).addEventListener('click', removeFromSession);

    const deleteButton = button({ class: "btn btn-primary bg-danger ", type: "submit" }, "Delete Session");
    (await deleteButton).addEventListener('click', deleteSession);

    const updateButton = button({ class: "btn btn-primary ", type: "submit" }, "Update Session");
    (await updateButton).addEventListener('click', updateSession);

    const renderPlayerRemoveLinks = await ShowPlayers(session,removeFromSession,"text-danger");

    return div(
        {class: "card mx-auto justify-content-center w-50 maxH-50 "},
        div(
            {class:"card-header"},
            h2({}, `Initial Capacity: ${session.players.length}/${session.capacity}`),
            h2({}, `Initial Date: ${session.date}`),
        ),
        div(
            {},
            label({ class: "form-label", for: "capacity" }, "New Capacity"),
            input({ class: "form-control", id: "capacityInput", placeholder: "(optional)" })
        ),
        div(
            {},
            label({ class: "form-label", for: "dateInput" }, "New Date"),
            input({ class: "form-control", type: "datetime-local", id: "dateInput"})
        ),

        div(
            {class:"card-footer d-flex justify-content-around"},
            updateButton,
            deleteButton,
        ),
        h2({},"Click to remove the selected player"),
        renderPlayerRemoveLinks
    )

}