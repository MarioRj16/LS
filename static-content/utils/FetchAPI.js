import {API_URL} from "./Configs.js";
import {USER_TOKEN} from "./Configs.js";



export async function FetchAPI(path, method = 'GET', bodyData = null) {
    try {
        const token = USER_TOKEN
        const headers = {
            'Authorization': `Bearer ${token}`, // Include the bearer token in the Authorization header
            'Content-Type': 'application/json' // Set content type if needed
        };
        let init ={}
        console.log(bodyData)
        if(bodyData == null){
            init= {
                method: method,
                headers: headers
            }
        }else {
            init = {
                method: method,
                headers: headers,
                body: JSON.stringify(bodyData)
            }
        }

        const response = await fetch(`${API_URL}${path}`, init)
        let json = await response.json()
        return json;
    } catch (e) {
        console.log(e)
        //TODO(throw alert or something)
    }
}


export function objectToQueryString(obj) {
    const parts = [];
    for (const key in obj) {
        if (obj.hasOwnProperty(key)) {
            const value = obj[key];
            parts.push(`${encodeURIComponent(key)}=${encodeURIComponent(value)}`);
        }
    }
    return parts.length > 0 ? `?${parts.join('&')}` : '';
}
