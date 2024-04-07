
export function render(element){
    const main = document.getElementById("mainContent")
    main.replaceChildren(element)
}

export function parseUrl(urll){
    const url= new URL(urll,window.location.origin)
    const path = url.pathname
    const query = {}
    url.searchParams.forEach(
        (value,key) =>
        {
            query[key]=value
    })
    return {path,query}
}