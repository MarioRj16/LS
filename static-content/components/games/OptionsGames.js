import {datalist, div, input} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";
import {createElement} from "../../utils/Utils.js";

export async function OptionsGames() {
    const dataList= await datalist({ id: "games" })
    const textInput = await input({
        type: "text",
        class: "form-control",
        id: "gameInput",
        placeholder: "Game Name",
        list: "games"
    });

    textInput.addEventListener('input', async () => {
        await updateOptions(dataList)
    });

    return div({},textInput,dataList)
}

async function updateOptions(dataList){
    const input= document.getElementById('gameInput').value;
    if(input.length < 3) return;
    FetchAPI(`/games?name=${input}`).then(obj => {
        const games = obj.games
        //try to erase this by children
        dataList.innerHTML = '';
        for (const game of games) {
            createElement("option",({value: game.name, id: game.name, accessKey: game.id}))
                .then(option => dataList.appendChild(option))
        }
    })
}
