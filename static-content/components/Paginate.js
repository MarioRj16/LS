export async function changePage(jump, path, query) {
    const queryParams = new URLSearchParams(query);
    const skipParam = queryParams.get("skip");
    const currentLimit = queryParams.get("limit");

    const defaultLimit = 30;
    const currentSkip = skipParam ? parseInt(skipParam) : 0;
    const limit = currentLimit ? parseInt(currentLimit) : defaultLimit;

    const newSkip = currentSkip + (jump * limit);

    if (newSkip < 0) {
        alert("You are on the first page");
        return;
    }

    queryParams.set("skip", newSkip);

    window.location.href = `#${path}?${queryParams.toString()}`;
}