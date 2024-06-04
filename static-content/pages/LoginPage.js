import {button, div, h1, input, label, a} from "../utils/Elements.js";
import {FetchAPI} from "../utils/FetchAPI.js";
import {storeUser} from "../utils/Utils.js";

export async function LoginPage(state){

    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Enter");
    (await submitButton).addEventListener('click', handleFormSubmit);

    async function handleFormSubmit(event) {
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
        console.log(user)
        if(user.token) {
            storeUser(user)
            window.location.href = `#home`;
        }
            else alert("Invalid email or password.");
    }

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Login")
        ),
        div(
            { class: "card-body d-flex flex-column gap-4" },
            div(
                {},
                label({ class: "form-label", for: "emailInput" }, "Email "),
                input({ class: "form-control", id: "emailInput", type: "email" })
            ),
            div(
                {},
                label({ class: "form-label", for: "passwordInput" }, "Password "),
                input({ class: "form-control", id: "passwordInput", type: "password" })
            ),

            div(
                { class: "mx-auto" },
                submitButton
            ),
            div(
                { class: "text-center mt-3" },
                a({ href: "#register", class: "text-decoration-none" }, "Don't have an account?")
            )
        )
    );
}
