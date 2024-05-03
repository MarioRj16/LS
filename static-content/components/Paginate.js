import {DEFAULT_LIMIT, DEFAULT_SKIP} from "../utils/Configs.js";

export async function changePage(jump, path, query) {
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

    window.location.href = `#${path}?${queryParams.toString()}`;
}