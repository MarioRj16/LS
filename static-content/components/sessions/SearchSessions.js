export function SearchSessions(){
    const gameInput = document.getElementById('gameInput').value;
    const dateInput = document.getElementById('dateInput').value;
    const stateInput = document.getElementById('stateInput').checked;
    const playerIdInput = document.getElementById('playerIdInput').value;


    const searchCriteria = {
        game: gameInput ? parseInt(gameInput) : null,
        date: dateInput ? new Date(dateInput) : null,
        state: stateInput,
        playerId: playerIdInput ? parseInt(playerIdInput) : null
    };
    window.location.href = `#sessions?${new URLSearchParams(searchCriteria).toString()}`;

}