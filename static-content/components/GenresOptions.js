import {div, option, select} from "../utils/Elements.js";


export async function GenresOptions(genres) {

        // Map each genre to an <option> element
    const optionsContainer = select({ id: "genreInput", multiple: true, placeholder: "Select genres (optional)" },);
    console.log(genres.size)
    // Map each genre to an <option> element and append to the optionsContainer <div>
    genres.forEach(genre => {
        const optionElement = option({ value: genre.id }, genre.name);
        optionsContainer.appendChild(optionElement);
    });

    // Return the <div> containing all the <option> elements
    return div({ class: "select-input" },

    optionsContainer

    );
}