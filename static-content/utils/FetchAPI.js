import {API_URL} from "./Utils.js";

export async function FetchAPI(path){
    try{
        const token = '00000000-0000-0000-0000-000000000000'
        const headers = {
            'Authorization': `Bearer ${token}`, // Include the bearer token in the Authorization header
            'Content-Type': 'application/json' // Set content type if needed
        };
        const response = await fetch(`${API_URL}${path}`, {
            method:'GET',
            headers:headers})
        console.log("resposta",response)
        let json = await response.json()
       // console.log(json)
        if(response.ok) return json;
    } catch (e){
        console.log(e)
        console.log("sou paneleiro")
        //TODO(throw alert or something)
    }
}