import {button, div, h1, input, label, a} from "../utils/Elements.js";
import {FetchAPI} from "../utils/FetchAPI.js";

export async function RegisterPage(state){

    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Register");
    (await submitButton).addEventListener('click', handleFormSubmit);

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

    return div(
        { class: "card mx-auto justify-content-center w-50 maxH-50" },
        div(
            { class: "card-header" },
            h1({}, "Register")
        ),
        div(
            { class: "card-body d-flex flex-column gap-4" },
            div(
                {},
                label({ class: "form-label", for: "usernameInput" }, "Username"),
                input({ class: "form-control", id: "usernameInput" })
            ),
            div(
                {},
                label({ class: "form-label", for: "emailInput" }, "Email"),
                input({ class: "form-control", id: "emailInput", type: "email" })
            ),
            div(
                {},
                label({ class: "form-label", for: "passwordInput" }, "Password"),
                input({ class: "form-control", id: "passwordInput", type: "password" })
            ),
            div(
                {},
                label({ class: "form-label", for: "confirmPasswordInput" }, "Confirm Password"),
                input({ class: "form-control", id: "confirmPasswordInput", type: "password" })
            ),

            div(
                { class: "mx-auto" },
                submitButton
            ),
            div(
                { class: "text-center mt-3" },
                a({ href: "#login", class: "text-decoration-none" }, "Already have an account?")
            )
        )
    );
}
