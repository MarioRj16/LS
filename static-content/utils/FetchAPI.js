import {API_URL} from "./Utils.js";



export async function FetchAPI(path, method = 'GET', data = null) {
    try {
        const token = '4fd05dc8-2508-44e7-9b4c-9c5253027f11'
        const headers = {
            'Authorization': `Bearer ${token}`, // Include the bearer token in the Authorization header
            'Content-Type': 'application/json' // Set content type if needed
        };
        let init ={}
        if(data == null){
            init= {
                method: method,
                headers: headers
            }
        }else {
            init = {
                method: method,
                headers: headers,
                body: JSON.stringify(data)
            }
        }

        const response = await fetch(`${API_URL}${path}`, init)
        let json = await response.json()
        if (response.ok) return json;
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
