
const API_BASE_URL = `http://localhost:8000`

function getHome(mainContent){
    const h1 = document.createElement("h1")
    const searchGamesA = document.createElement("a")
    const searchSessionsA = document.createElement("a")
    const playerDetails = document.createElement("a")
    const text = document.createTextNode("Home")
    const searchGamesTxt = document.createTextNode("Search Games")
    const searchSessionsTxt = document.createTextNode("Search Sessions")
    const playerDetailsTxt = document.createTextNode("Player Details")

    h1.appendChild(text)
    searchSessionsA.appendChild(searchSessionsTxt)
    searchGamesA.appendChild(searchGamesTxt)
    playerDetails.appendChild(playerDetailsTxt)
    mainContent.replaceChildren(h1)
    mainContent.appendChild(searchGamesA)
    mainContent.appendChild(searchSessionsA)
    mainContent.appendChild(playerDetails)
}


function searchGames(mainContent){
    const developerInput = document.createElement("");
    const genresInput =document.createElement("");

    developerInput.setAttribute("type", "text");
    genresInput.setAttribute("type", "text");
}

function searchGamingSessions(mainContent){

}

function games(mainContent){

}

function gamingSessions(mainContent){

}

function gameDetails(mainContent){

}

function sessionDetails(mainContent){

}

function playerDetails(mainContent){

}

/**
 *  fetch(API_BASE_URL + "students/10")
 *         .then(res => res.json())
 *         .then(student => {
 *             const ulStd = document.createElement("ul")
 *
 *             const liName = document.createElement("li")
 *             const textName = document.createTextNode("Name : " + student.name)
 *             liName.appendChild(textName)
 *
 *             const liNumber = document.createElement("li")
 *             const textNumber = document.createTextNode("Number : " + student.number)
 *             liNumber.appendChild(textNumber)
 *
 *             ulStd.appendChild(liName)
 *             ulStd.appendChild(liNumber)
 *
 *             mainContent.replaceChildren(ulStd)
 *         })
 * @type {{getHome: getHome, searchGames: searchGames}}
 */

export const handlers = {
    getHome,
    searchGames,

}

export default handlers