import {button, div, h1, input, label, option, select} from "../../utils/Elements.js";
import {OptionsGames} from "../../components/games/OptionsGames.js";
import {SearchSessions} from "../../components/sessions/SearchSessions.js";
import {OptionsPlayers} from "../../components/players/OptionsPlayers.js";

export async function SessionsSearchPage(state) {
    const games= await OptionsGames()

    const players = await OptionsPlayers()

    return SearchSessions(games,players)
}
