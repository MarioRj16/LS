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

    queryParams.set("skip", newSkip);
     window.location.href = `#${path}?${queryParams.toString()}`;
}

export async function Paginate(query, path, hasPrevious, hasNext) {

    const previousButton = button(
        { class: "btn btn-primary", type: "button", ...(hasPrevious ? {} : { disabled: true }) },
        "Previous"
    );
    (await previousButton).addEventListener('click', previousPage);

    const nextButton = button(
        { class: "btn btn-primary", type: "button", ...(hasNext ? {} : { disabled: true }) },
        "Next"
    );
    (await nextButton).addEventListener('click', nextPage);

    function previousPage() {
        changePage(-1, path, query);
    }

    function nextPage() {
        changePage(1, path, query);
    }

    return div(
        { class: "d-flex justify-content-center gap-4" },
        previousButton,
        nextButton
    );
}
