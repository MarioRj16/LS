import {a, button, div, h2, input, label} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";
import {ShowPlayers} from "../players/ShowPlayers.js";

export async function UpdateSession(session){
    const removeFromButton = button({class: "btn btn-primary ", type: "submit"}, "Remove from Session");
    (await removeFromButton).addEventListener('click', removeFromSession);

    const deleteButton = button({ class: "btn btn-primary bg-danger ", type: "submit" }, "Delete Session");
    (await deleteButton).addEventListener('click', deleteSession);

    const updateButton = button({ class: "btn btn-primary ", type: "submit" }, "Update Session");
    (await updateButton).addEventListener('click', updateSession);

    const renderPlayerRemoveLinks = await ShowPlayers(session,removeFromSession,"text-danger");

    async function removeFromSession(playerId){
        const response = await FetchAPI(`/sessions/${session.id}/players/${playerId}`,`DELETE`)
        if(response.ok) {
            alert("Removed from Session Successfully")
            window.location.reload()
        }
        else alert(response.message)
    }


    async function deleteSession(){
        await FetchAPI(`/sessions/${id}`,`DELETE`)
        alert("Session Deleted Successfully Redirecting to Home")
        window.location.href = `#home`;
    }

    async function updateSession(){
        const capacityInput = document.getElementById('capacityInput').value;
        const dateInput = document.getElementById('dateInput').value;
        const params = {};
        if (capacityInput==null) {
            alert("Must fill capacity")
            return
        }
        params.capacity= parseInt(capacityInput);
        if (dateInput==null){
            alert("Must fill date")
            return
        }
        params.startingDate= new Date(dateInput).getTime();
        const response = await FetchAPI(`/sessions/${session.id}`,`PUT`, params);
        if(response.ok) alert("Session Updated Successfully")
        else alert(response.message)
        window.location.href= `#sessions/${session.id}`;
    }

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