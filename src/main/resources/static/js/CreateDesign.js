function createDesign() {
    const form = document.getElementById('design-form');
    const formData = new FormData(form);
    const subCategoryName = formData.get('subCategoryName');
    formData.delete('subCategoryName');
    const formDataObject = {};
    for (const [key, value] of formData.entries()) {
        formDataObject[key] = value;
    }

    // Get the image file separately
    const imageInput = document.getElementById('image');
    const imageFile = imageInput.files[0];

    // API request with subCategoryName as a query parameter
    const apiUrl = `http://localhost:4040/api/design?subCategoryName=${subCategoryName}`;

    // Create a new FormData object and append the image
    const combinedData = new FormData();
    combinedData.append('image', imageFile);

    // Add the data as a JSON string with a specified content type
//    const dataObject = {
//        designName: formData.get(''),
//        description: 'YourDescription',
//        creatorName: 'YourCreatorName'
//    };
    combinedData.append('data', new Blob([JSON.stringify(formDataObject)], { type: 'application/json' }));

    // Append the remaining form data to combinedData
    for (var pair of formData.entries()) {
        combinedData.append(pair[0], pair[1]);
    }

    fetch(apiUrl, {
        method: 'POST',
        body: combinedData
    })
    .then(response => {
        if (response.ok) {
            // Handle success
            alert('Design created successfully!');
        } else {
            // Handle error
            alert('Failed to create design');
        }
    })
    .catch(error => {
        console.error('API request error:', error);
    });
}



document.addEventListener("DOMContentLoaded", function () {
    const imageInput = document.getElementById("image");
    const selectedImage = document.getElementById("selectedImage");

    imageInput.addEventListener("change", function () {
        const selectedFile = imageInput.files[0];

        if (selectedFile) {
            const objectURL = URL.createObjectURL(selectedFile);
            selectedImage.src = objectURL;
        } else {
            selectedImage.src = ""; // Clear the image if no file is selected
        }
    });

    // Your existing code here...
});


// Function to suggest subcategories based on user input
let subCategoryNames = []; // Array to store subcategory names
const subCategoryInput = document.getElementById('subCategoryName');
const suggestionList = document.getElementById('suggestions');

// Fetch the list of subcategory names from the API
fetch('http://localhost:4040/api/subcategory/names', {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
    }
})
.then(response => response.json())
.then(data => {
    subCategoryNames = data; // Store subcategory names in the array
})
.catch(error => {
    console.error('API request error:', error);
});

// Function to suggest subcategories based on user input
function suggestSubCategories() {
    suggestionList.innerHTML = ''; // Clear previous suggestions

    const searchValue = subCategoryInput.value.toLowerCase();

    // Display the matched subcategory suggestions with a border
    subCategoryNames.forEach(subCategory => {
        if (subCategory.toLowerCase().startsWith(searchValue)) {
            const suggestionItem = document.createElement('div');
            suggestionItem.textContent = subCategory;
            suggestionItem.classList.add('suggestion-item');
            suggestionItem.addEventListener('click', () => {
                // When a suggestion is clicked, fill the input with the suggestion
                subCategoryInput.value = subCategory;
                suggestionList.innerHTML = ''; // Clear suggestions
            });
            suggestionList.appendChild(suggestionItem);
        }
    });

    // Show the suggestions if there are any
    if (suggestionList.children.length > 0) {
        suggestionList.style.display = 'block';
    } else {
        suggestionList.style.display = 'none';
    }
}

// Listen for clicks on the document to hide suggestions when clicking elsewhere
document.addEventListener('click', (event) => {
    if (event.target !== subCategoryInput && event.target !== suggestionList) {
        suggestionList.style.display = 'none'; // Hide suggestions
    }
});

// Prevent clicks within the suggestions list from hiding the suggestions
suggestionList.addEventListener('click', (event) => {
    event.stopPropagation();
});
