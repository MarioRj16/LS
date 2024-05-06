import { div, select, option, input, label } from "../utils/Elements.js";

export async function GenresOptions(genres) {
    const container = document.createElement('div');
    container.classList.add("multiselect");
    container.id="genreInput"// Add CSS class for styling

    // Create select box
    const selectBox = document.createElement('div');
    selectBox.classList.add("selectBox");

    const selectElement = document.createElement('select');
    selectElement.classList.add("select-input");
    const defaultOption = document.createElement('option');
    defaultOption.textContent = "Select genres";
    defaultOption.disabled = true;
    defaultOption.selected = true;
    selectElement.appendChild(defaultOption);
    genres.forEach(genre => {
        const optionElement = document.createElement('option');
        optionElement.value = genre.genreId;
        optionElement.textContent = genre.name;
        selectElement.appendChild(optionElement);
    });

    selectBox.appendChild(selectElement);

    // Create overlay for expanding checkboxes
    const overSelect = document.createElement('div');
    overSelect.classList.add("overSelect");

    selectBox.appendChild(overSelect);

    // Create checkboxes container
    const checkboxesContainer = document.createElement('div');
    checkboxesContainer.id = "checkboxes";
    checkboxesContainer.style.display = "none"; // Initially hidden

    genres.forEach(genre => {
        const checkboxLabel = document.createElement('label');
        checkboxLabel.setAttribute('for', `genre_${genre.genreId}`);

        const checkboxInput = document.createElement('input');
        checkboxInput.type = "checkbox";
        checkboxInput.id = `genre_${genre.genreId}`;
        checkboxInput.value = genre.genreId;

        checkboxLabel.appendChild(checkboxInput);
        checkboxLabel.appendChild(document.createTextNode(genre.name));
        checkboxesContainer.appendChild(checkboxLabel);
    });

    // Toggle function to show/hide checkboxes
    selectBox.addEventListener('click', () => {
        checkboxesContainer.style.display = checkboxesContainer.style.display === "block" ? "none" : "block";
    });

    container.appendChild(selectBox);
    container.appendChild(checkboxesContainer);

    return container;
}


export function GenresOptionsInputs(){
    const genreInput = [];
    const checkboxes = document.querySelectorAll('input[type="checkbox"][id^="genre_"]');
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            const genreId = checkbox.value;
            genreInput.push(genreId);
        }
    });
    return genreInput;
}