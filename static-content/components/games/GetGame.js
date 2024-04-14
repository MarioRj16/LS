import {button, div, h1, h2, h3, h4, h5} from "../../utils/Elements.js";

export async function GetGame(game){
    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Search");
    (await submitButton).addEventListener('click', searchGameSession);
    function searchGameSession(){
        window.location.href = `#sessions?gameId=${game.id}`;
    }
    const renderGenresHeaders = () => {
        const genresElement = document.createElement("genreList")
        game.genres.forEach(genre => {
            const addGenre = document.createElement("genre")
            addGenre.value = genre.id
            addGenre.textContent = genre.name
            genresElement.appendChild(addGenre)
            }
        )
        return genresElement
    };


    return div(
        h1(game.name),
        h2(game.developer),
        h5("","Genres:"),
        renderGenresHeaders(),
        submitButton
    )
}