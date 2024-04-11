import {API_URL} from "./Utils.js";

export async function FetchAPI(path){
    try{
        const response = await fetch(`${API_URL}${path}`)
        let json = await response.json()
        if(response.ok) return json;
    } catch (e){
        console.log(e)
        //TODO(throw alert or something)
    }
}