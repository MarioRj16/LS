import {div} from "../utils/Elements.js";

export async function AllCards(cards) {
    return div({class:"card-container"},
        ...cards)
        ;
}