import {div, h1} from "../utils/Elements.js";

export async function AllCards(cards,paginate,header) {
    const containerCards= div({class:"card-container"}, ...cards)

    return div(
        {class:"card"},
        h1({ class: "card-header d-flex justify-content-center"}, header),
        div(
            {class:"card-body"},
            containerCards,
            paginate
        )
    );
}