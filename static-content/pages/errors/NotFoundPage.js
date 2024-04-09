import {h1} from "../../utils/Elements";

export async function NotFoundPage(state){
    return h1(`Path ${state.path} not found`)
}