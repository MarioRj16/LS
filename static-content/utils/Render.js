
export function render(element){
    console.log("Element in render:", element);
    const main = document.getElementById("mainContent")
    main.replaceChildren(element)
}

export function parseUrl(origin){
    const url= new URL(origin,window.location.origin)
    const path = url.pathname
    const query = {}
    url.searchParams.forEach(
        (value,key) =>
        {
            query[key]=value
    })
    return {path,query}
}