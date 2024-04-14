import { div, select, option } from "../utils/Elements.js";

export async function GenresOptions(genres) {

        const selectElement = document.createElement('select');
        selectElement.id = "genreInput";
        selectElement.multiple = true;
        selectElement.placeholder = "Select genres (optional)";

        genres.forEach(genre => {
            const optionElement = document.createElement('option');
            optionElement.value = genre.genreId;
            optionElement.textContent = genre.genreName;
            selectElement.appendChild(optionElement);
        });

        return selectElement;

}
