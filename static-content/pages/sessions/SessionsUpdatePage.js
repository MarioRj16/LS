import {button, div, input, label} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";

export async function SessionsUpdatePage(state){
    const id = state.params.id;


    const removeFromButton = button({class: "btn btn-primary ", type: "submit"}, "Remove from Session");
   (await removeFromButton).addEventListener('click', removeFromSession);

    async function removeFromSession(playerId){
        await FetchAPI(`/sessions/${session.id}/players/${playerId}`,`DELETE`)
        alert("Removed from Session Successfully")
        window.location.reload()
    }
    const deleteButton = button({ class: "btn btn-primary ", type: "submit" }, "Delete Session");
    (await deleteButton).addEventListener('click', deleteSession);

    const updateButton = button({ class: "btn btn-primary ", type: "submit" }, "Update Session");
    (await updateButton).addEventListener('click', updateSession);



    async function deleteSession(){
        await FetchAPI(`/sessions/${id}`,`DELETE`)
        alert("Session Deleted Successfully Redirecting to Home")
        window.location.href = `#home`;
    }

    async function updateSession(){
        const capacityInput = document.getElementById('capacityInput').value;
        const dateInput = document.getElementById('dateInput').value;
        const params = {};
        params.capacity= parseInt(capacityInput);
        params.startingDate= new Date(dateInput).getTime();
        await FetchAPI(`/sessions/${id}`,`PUT`, params);
        alert("Session Updated Successfully")
        window.location.href= `#sessions/${id}`;
    }

    return div(
        {class: "card mx-auto justify-content-center w-50 maxH-50 "},
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

       // removeFromButton,
    )

}