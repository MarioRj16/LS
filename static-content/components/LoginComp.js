import {a, button, div, h1, input, label} from "../utils/Elements.js";

export async function LoginComp(handleFormSubmit){

    const submitButton = button({ class: "btn btn-primary", type: "submit" }, "Enter");
    (await submitButton).addEventListener('click', handleFormSubmit);

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