import {div, h1, h2} from "../../utils/Elements.js";

export async function GetPlayer(state){
    return div(
        div(
            { class: "text-center" },
            h2({}, `Email: ${state.email}`),
            h2({}, `Name: ${state.name}`)
        )
    );

}