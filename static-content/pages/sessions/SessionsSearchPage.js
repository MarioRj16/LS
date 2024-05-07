import {button, div, h1, input, label, option, select} from "../../utils/Elements.js";
import {OptionsGames} from "../../components/games/OptionsGames.js";
import {SearchSessions} from "../../components/sessions/SearchSessions.js";

export async function SessionsSearchPage(state) {
    const games= await OptionsGames()

    return SearchSessions(games)
}
