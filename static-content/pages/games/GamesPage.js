import { div, h1 } from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {objectToQueryString} from "../../utils/FetchAPI.js";
import {Paginate} from "../../components/Paginate.js";
import {CardGame} from "../../components/games/CardGame.js";
import {AllCards} from "../../components/AllCards.js";

export async function GamesPage(state) {
    const gamesResponse = await FetchAPI(`/games${objectToQueryString(state.query)}`);
    const games = gamesResponse.games;

    const cards = games.map(game => CardGame(game));

    const paginatedCards = await AllCards(cards);

    const paginate = Paginate(state.query)

    return div(
        {class:"card"},
        h1({ class: "card-header d-flex justify-content-center" }, "Games"),
        div(
            { class: "game-list" },
            paginatedCards
        ),
        paginate
    );
}
