
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


function searchGames(mainContent) {
    // Create input elements for developer, genres, skip, limit
    const developerInput = createTextInput("Enter developer...");
    const genresInput = createTextInput("Enter genres...");
    const skipInput = createNumberInput("Skip:", 0);
    const limitInput = createNumberInput("Limit:", 10);

    // Create search button
    const searchButton = document.createElement("button");
    searchButton.textContent = "Search";

    // Add event listener to the search button
    searchButton.addEventListener("click", function() {
        const developerQuery = developerInput.value.trim().toLowerCase();
        const genresQuery = genresInput.value.trim().toLowerCase();
        const skipValue = parseInt(skipInput.value) || 0;
        const limitValue = parseInt(limitInput.value) || mainContent.length;

        // Perform search based on the input values
        const filteredGames = mainContent.filter(game => {
            const developerMatch = developerQuery === '' || game.developer.toLowerCase().includes(developerQuery);
            const genresMatch = genresQuery === '' || game.genres.some(genre => genre.toLowerCase().includes(genresQuery));
            return developerMatch && genresMatch;
        }).slice(skipValue, skipValue + limitValue); // Apply skip and limit

        // Render filtered games (example: replace this with your rendering logic)
        renderFilteredGames(filteredGames);
    });

    // Append inputs and button to a container in the DOM
    const container = document.createElement("div");
    container.appendChild(developerInput);
    container.appendChild(genresInput);
    container.appendChild(skipInput);
    container.appendChild(limitInput);
    container.appendChild(searchButton);

    // Assuming `mainContent` is an array of game objects, render initial list of games
    renderFilteredGames(mainContent);

    // Append the container to the document
    document.body.appendChild(container);
}

// Helper function to create a text input
function createTextInput(placeholder) {
    const input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("placeholder", placeholder);
    return input;
}

// Helper function to create a number input
function createNumberInput(labelText, defaultValue) {
    const container = document.createElement("div");

    const label = document.createElement("label");
    label.textContent = labelText;

    const input = document.createElement("input");
    input.setAttribute("type", "number");
    input.setAttribute("value", defaultValue.toString());

    container.appendChild(label);
    container.appendChild(input);
    return container;
}

// Example renderFilteredGames function (replace with your implementation)
function renderFilteredGames(games) {
    const gameListContainer = document.getElementById("game-list");
    gameListContainer.innerHTML = '';

    games.forEach(game => {
        const gameElement = document.createElement("div");
        gameElement.textContent = `${game.title} by ${game.developer}`;
        gameElement.classList.add("game-item");

        // Add click event listener to each game element
        gameElement.addEventListener("click", () => {
            // Redirect to game details page
            redirectToGameDetailsPage(game);
        });

        gameListContainer.appendChild(gameElement);
    });
}

// Function to redirect to game details page
function redirectToGameDetailsPage(game) {
    // Construct the URL for the game details page (example: game-details.html?id=gameId)
    const gameId = encodeURIComponent(game.id); // Assuming game object has an 'id' property
    const url = `game-details.html?id=${gameId}`;

    // Redirect to the game details page
    window.location.href = url;
}

function searchGamingSessions(mainContent){
    const gameInput = document.createElement("");
    const dateInput =document.createElement("");
    const stateInput =document.createElement("");
    const playerIdInput =document.createElement("");

    gameInput.setAttribute("type", "text");
    dateInput.setAttribute("type", "text");
    stateInput.setAttribute("type", "text");
    playerIdInput.setAttribute("type", "text");
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