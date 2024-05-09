import {DEFAULT_LIMIT, DEFAULT_SKIP} from "../utils/Configs.js";
import {button, div} from "../utils/Elements.js";
import {FetchAPI} from "../utils/FetchAPI.js";

async function changePage(jump, path, query) {
    const queryParams = new URLSearchParams(query);
    const skipParam = queryParams.get("skip");
    const currentLimit = queryParams.get("limit");

    const currentSkip = skipParam ? parseInt(skipParam) : DEFAULT_SKIP;
    const limit = currentLimit ? parseInt(currentLimit) : DEFAULT_LIMIT;

    const newSkip = currentSkip + (jump * limit);

    if (newSkip < 0) {
        alert("You are on the first page");
        return;
    }

    queryParams.set("skip", newSkip);

    const response = await FetchAPI(`/${path}?${queryParams.toString()}`)
    if(response.total==0) alert("There's no more pages")
    else window.location.href = `#${path}?${queryParams.toString()}`;
}

export async function Paginate(query){

    const previousButton = button(
        { class: "btn btn-primary", type: "button" },
        "Previous"
    );
    (await previousButton).addEventListener('click', previousPage)

    const nextButton = button(
        { class: "btn btn-primary", type: "button" },
        "Next"
    );
    (await nextButton).addEventListener('click', nextPage)


    function previousPage(){
        changePage(-1, "games", query)
    }

    function nextPage(){
        changePage(1, "games", query)
    }

    return div(
        {class: "d-flex justify-content-center gap-4"},
        previousButton,
        nextButton
    )
}