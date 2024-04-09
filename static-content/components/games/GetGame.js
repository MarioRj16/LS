import {div, h1, h2, h3} from "../../utils/Elements";

export async function GetGame(game){

//TODO(add on click to redirect to sessions)
    const renderGenresHeaders = () => {
        return game.genres.map((genre) => {
            return h3({}, `Genres: ${genre.name}`);
        });
    };


    return div(
        h1(game.name),
        h2(game.developer),
        div(
            renderGenresHeaders()
        )
    )
}