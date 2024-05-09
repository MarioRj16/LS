import {datalist, div, input} from "../../utils/Elements.js";
import {FetchAPI} from "../../utils/FetchAPI.js";
import {createElement} from "../../utils/Utils.js";

export async function OptionsPlayers() {
    const dataList= await datalist({ id: "players" })
    const textInput = await input({
        type: "text",
        class: "form-control",
        id: "playerInput",
        placeholder: "Player Name",
        list: "players"
    });

    textInput.addEventListener('input', async () => {
        await updateOptions(dataList)
    });

    return div({},textInput,dataList)
}

async function updateOptions(dataList){
    const input= document.getElementById('playerInput').value;
    if(input.length < 3) return;
    FetchAPI(`/players?username=${input}`).then(obj => {
        const players = obj.players
        //try to erase this by children
        dataList.innerHTML = '';
        for (const player of players) {
            createElement("option",({value: player.name, id: player.name}))
                .then(option => dataList.appendChild(option))
        }
    })
}
