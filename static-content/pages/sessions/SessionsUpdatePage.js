import {FetchAPI} from "../../utils/FetchAPI.js";
import {UpdateSession} from "../../components/sessions/UpdateSession.js";

export async function SessionsUpdatePage(state){
    const id = state.params.id;

    const session = await FetchAPI(`/sessions/${id}`)

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



    return UpdateSession(session,removeFromSession,deleteSession,updateSession)
}