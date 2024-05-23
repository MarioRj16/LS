import { div, h1 } from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {objectToQueryString} from "../../utils/FetchAPI.js";
import {Paginate} from "../../components/Paginate.js";
import {CardGame} from "../../components/games/CardGame.js";
import {AllCards} from "../../components/AllCards.js";

export async function GamesPage(state) {
    const games = (await FetchAPI(`/games${objectToQueryString(state.query)}`));

    const cards = games.games.map(game => CardGame(game));

    const paginate = Paginate(state.query,"games",games.hasPrevious,games.hasNext)

    return AllCards(cards,paginate,"Games");
}
