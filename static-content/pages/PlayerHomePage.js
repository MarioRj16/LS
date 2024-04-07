import {div, h1} from "../utils/Elements";


export async function PlayerHomePage(state){
    return div(
        h1({class: "Title"}, "PlayerHome" )
    )
}