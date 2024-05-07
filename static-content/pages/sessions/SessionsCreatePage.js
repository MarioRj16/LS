import { button, div, h1, input, label} from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {OptionsGames} from "../../components/games/OptionsGames.js";
import {CreateSession} from "../../components/sessions/CreateSession.js";

export async function SessionsCreatePage(state) {
    const games=await OptionsGames()

    return CreateSession(games)
}
