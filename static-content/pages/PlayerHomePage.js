import {button, div, h1, h2} from "../utils/Elements.js";
import {HomeComp} from "../components/HomeComp.js";


export async function PlayerHomePage(state){
    function redirectGameCreate(){
        window.location.href = `#games/create`;
    }

    function redirectSessionCreate(){
        window.location.href = `#sessions/create`;
    }

    return HomeComp(redirectGameCreate,redirectSessionCreate);
}