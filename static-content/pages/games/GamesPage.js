import { div, h1 } from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {objectToQueryString} from "../../utils/FetchAPI.js";
import {Paginate} from "../../components/Paginate.js";
import {CardGame} from "../../components/games/CardGame.js";
import {AllCards} from "../../components/AllCards.js";

export async function GamesPage(state) {
    const games = (await FetchAPI(`/games${objectToQueryString(state.query)}`)).games;

    const cards = games.map(game => CardGame(game));

    const paginate = Paginate(state.query)

    return AllCards(cards,paginate,"Games");
}
