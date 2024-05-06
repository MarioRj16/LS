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