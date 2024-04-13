import {div, h1, h2} from "../../utils/Elements.js";

export async function GetPlayer(state){

    return div(
        {class: "row justify-content-evenly"},
        h1({class:""},`Player`),
        div(
            h2({}, `Email: ${state.email}`),
            h2({}, `Name: ${state.name}`)
        )
    )
}