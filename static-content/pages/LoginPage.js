import {button, div, h1, input, label, a} from "../utils/Elements.js";
import {FetchAPI} from "../utils/FetchAPI.js";
import {storeUser} from "../utils/Utils.js";
import {LoginComp} from "../components/LoginComp.js";

export async function LoginPage(state){

    async function handleFormSubmit() {
        event.preventDefault();

        const emailInput = document.getElementById('emailInput').value;
        const passwordInput = document.getElementById('passwordInput').value;

        if (!emailInput.match(/^[A-Za-z\d+_.-]+@(.+)$/)) {
            alert("Invalid email.");
            return;
        }

        if (!passwordInput) {
            alert("Please enter a password.");
            return;
        }

        const user= await FetchAPI("/players/login", "POST", {email: emailInput, password: passwordInput})

        if(user.token) {
            storeUser(user)
            window.location.href = `#home`;
        }
            else alert("Invalid email or password.");
    }

    return LoginComp(handleFormSubmit);
}
