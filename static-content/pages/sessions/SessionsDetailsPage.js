import {FetchAPI} from "../../utils/FetchAPI.js";
import {GetSession} from "../../components/sessions/GetSession.js";
import {getStoredUser} from "../../utils/Utils.js";

export async function SessionsDetailsPage(state){
    const id = state.params.id;

    const session = await FetchAPI(`/sessions/${id}`)

    const host = await FetchAPI(`/players/${session.host}`);

    async function updateSession(){
        window.location.href=`#sessions/${session.id}/update`
    }

    async function leaveSession(){
        console.log("OII")
        const request= await FetchAPI(`/sessions/${session.id}/players/${session.host}`,`DELETE`)
        if(request==undefined){
            alert("Left Session Successfully")
            window.location.reload()
        }else alert(request.message)
    }

    async function joinSession(){
        await FetchAPI(`/sessions/${session.id}`,`POST`)
        alert("Joined Session Successfully")
        window.location.reload()
    }

    return GetSession(session, session.players, host, getStoredUser().playerId,updateSession,leaveSession,joinSession)
}