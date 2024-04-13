import {div, h1} from "../utils/Elements.js";


export async function PlayerHomePage(state){

    return await div(
        h1({class: "Title"}, "PlayerHome" )
    )
}