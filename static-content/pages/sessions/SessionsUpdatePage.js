import {button, div, input, label} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";

export async function SessionsUpdatePage(state){
    const removeFromButton = button({class: "btn btn-primary ", type: "submit"}, "Remove from Session");
   (await removeFromButton).addEventListener('click', removeFromSession);

    async function removeFromSession(playerId){
        await FetchAPI(`/sessions/${session.id}/players/${playerId}`,`DELETE`)
        //add alert
    }
    const deleteButton = button({ class: "btn btn-primary ", type: "submit" }, "Delete Session");
    (await deleteButton).addEventListener('click', deleteSession);

    const updateButton = button({ class: "btn btn-primary ", type: "submit" }, "Update Session");
    (await updateButton).addEventListener('click', updateSession);



    async function deleteSession(){
        await FetchAPI(`/sessions/${session.id}`,`DELETE`)
        window.location.href = `#home`;
    }

    async function updateSession(){
        //create elements for update
    }

    return div(
        {class: "card"},
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

        removeFromButton,
    )

}