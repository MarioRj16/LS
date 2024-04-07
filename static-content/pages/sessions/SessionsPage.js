import {div, h1} from "../../utils/Elements";
import {FetchAPI} from "../../utils/FetchAPI";

export async function SessionsPage(state){
    const sessions= await FetchAPI(`/sessions?${state.query}`)


    return div(
        h1({class:""},`Sessions`),
        div(

        )
    )
}