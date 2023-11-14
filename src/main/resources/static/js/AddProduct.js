async function addProduct() {
    const designName = document.getElementById("designName").value;
    const quantity = parseInt(document.getElementById("quantity").value);
    const currentCost = parseFloat(document.getElementById("currentCost").value);
    const sizeName = document.getElementById("sizeName").value;
    const color = document.getElementById("color").value;

    if (!designName || !quantity || !currentCost || !sizeName || !color) {
        alert("All fields are required!");
        return;
    }

    if (isNaN(quantity) || quantity < 1) {
        alert("Quantity must be a positive integer!");
        return;
    }

    if (isNaN(currentCost) || currentCost <= 0) {
        alert("Cost must be a positive number!");
        return;
    }

    const productData = {
        quantity: quantity,
        cost: currentCost,
        designName: designName,
        size: {
            sizeName: sizeName,
        },
        color: color,
    };

    try {
        const response = await fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productData),
        });

        if (response.status >= 200 && response.status<300) {
            alert("Product created successfully!");
            document.getElementById("quantity").value = "";
            document.getElementById("currentCost").value = "";
            document.getElementById("sizeName").value = "";
            document.getElementById("color").value = "";
        } else {
            alert("Failed to create the product. Please try again.");
        }
    } catch (error) {
        alert("An error occurred while sending the request.");
        console.error(error);
    }
}

const apiEndpoint = 'http://localhost:4040/api/product-list';
document.getElementById('designName').addEventListener('mouseenter', function() {
            showAllOptions();
        });
// Function to suggest subcategories based on user input
let options = []; // Array to store subcategory names

fetch('http://localhost:4040/api/design/names', {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
    }
})
.then(response => response.json())
.then(data => {
    options = data; // Store subcategory names in the array
})
.catch(error => {
    console.error('API request error:', error);
});

  function filterOptions() {
const inputText = document.getElementById('designName').value;

     const regex = new RegExp(inputText, 'i');
      const filteredOptions = options.filter(option => regex.test(option));

      const optionsList = document.getElementById('options-list');
      optionsList.innerHTML = '';

      if (filteredOptions.length > 0) {
        filteredOptions.forEach(option => {
          const optionElement = document.createElement('div');
          optionElement.textContent = option;
          optionElement.addEventListener('click', () => {
            document.getElementById('designName').value = option;
            optionsList.style.display = 'none';
          });
          optionsList.appendChild(optionElement);
        });
        optionsList.style.display = 'block';
      } else {
        optionsList.style.display = 'none';
      }
    }

     function showAllOptions() {
        const optionsList = document.getElementById('options-list');
        if (optionsList.style.display === 'none') {
          optionsList.innerHTML = '';
          options.forEach(option => {
            const optionElement = document.createElement('div');
            optionElement.textContent = option;
            optionElement.addEventListener('click', () => {
              document.getElementById('designName').value = option;
              optionsList.style.display = 'none';
            });
            optionsList.appendChild(optionElement);
          });
          optionsList.style.display = 'block';
        }
      }

      document.addEventListener('click', (event) => {
        const optionsList = document.getElementById('options-list');
        if (!event.target.matches('#subCategoryName') && !event.target.matches('#options-list div')) {
          optionsList.style.display = 'none';
        }
      });
