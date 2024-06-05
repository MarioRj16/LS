import {button, div, h1, input, label, a} from "../utils/Elements.js";
import {FetchAPI} from "../utils/FetchAPI.js";
import {RegisterComp} from "../components/RegisterComp.js";

export async function RegisterPage(state){

    async function handleFormSubmit(event) {
        event.preventDefault();
        const usernameInput = document.getElementById('usernameInput').value;
        const emailInput = document.getElementById('emailInput').value;
        const passwordInput = document.getElementById('passwordInput').value;
        const confirmPasswordInput = document.getElementById('confirmPasswordInput').value;

        if (!usernameInput) {
            alert("Please enter a username.");
            return;
        }

        if (!emailInput.match(/^[A-Za-z\d+_.-]+@(.+)$/)) {
            alert("Invalid email.");
            return;
        }

        if (!passwordInput) {
            alert("Please enter a password.");
            return;
        }

        if (passwordInput !== confirmPasswordInput) {
            alert("Passwords do not match.");
            return;
        }

        const user = await FetchAPI("/players", "POST", {name:usernameInput, email: emailInput, password: passwordInput})
        if (user.token){
            alert("Registration successful! Please log in.");
            window.location.href = `#login`;
        }
        else alert(user.message);
    }

    return RegisterComp(handleFormSubmit);
}
