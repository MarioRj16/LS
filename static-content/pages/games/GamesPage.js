import { div, h1, a, h2, h3, button, form, label, input } from "../../utils/Elements.js";
import { FetchAPI } from "../../utils/FetchAPI.js";
import {objectToQueryString} from "../../utils/FetchAPI.js";

export async function GamesPage(state) {
    console.log(state.query)
    const gamesResponse = await FetchAPI(`/games${objectToQueryString(state.query)}`);
    const games = gamesResponse.games;
    function handleClick(gameId) {
        return () => {
            console.log(gameId);
            window.location.href = `#games/${gameId}`;
        };
    }

    async function gameCard(game) {
        const detailsButton = button(
            { class: "btn btn-primary", type: "button" },
            "Details"
        );

        (await detailsButton).addEventListener('click', handleClick(game.id))
        return div(
            {class: "card mx-auto justify-content-center w-50 maxH-50 m-2"},
                div(
                    { class: "game-details" },
                    h3({class: "card-header text-center"}, `${game.name}`),
                    div(
                        { class: "card-body text-center"},
                        h3({class:"justify-content-center"}, `Developer: ${game.developer}`),
                        detailsButton
                    )

            )
        )
    }

    const previousButton = button(
        { class: "btn btn-primary", type: "button" },
        "Previous"
    );
    (await previousButton).addEventListener('click', previousPage)
    function changePage(jump){
        const skipParam = new URLSearchParams(state.query).get("skip");
        const currentLimit = new URLSearchParams(state.query).get("limit");
        console.log(`${skipParam},${currentLimit}`)
        const currentSkip = skipParam ? parseInt(skipParam) : 1;
        const newLimit = currentLimit ? parseInt(currentLimit) : 30
        const newSkip = currentSkip + (jump*currentLimit);
        if (nextPage < 0) {
            alert("You are on the first page");
            return;
        }
        window.location.href = `#games?skip=${newSkip}&limit=${newLimit}`
        return (newSkip,newLimit)
    }
    function previousPage(){
        const x = changePage(-1)
        paginate(cards,x.first,x.second)
    }

    const nextButton = button(
        { class: "btn btn-primary", type: "button" },
        "Next"
    );
    (await nextButton).addEventListener('click', nextPage)

    async function paginate(cards) {
        return div({class:"card-container"},
            ...cards)
            ;
    }

    const cards = games.map(game => gameCard(game));

    const paginatedCards = await paginate(cards);

    if(!Array.isArray(games) || games.length === 0){
        return div(h1({class:"text-center"}, "No games found"));
    }

    return div(
        h1({ class: "d-flex justify-content-center" }, "Games"),
        div(
            { class: "game-list" },
            paginatedCards
        ),
        div(
            previousButton,
            nextButton
        )
    );
}
