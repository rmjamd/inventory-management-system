async function addProduct() {
    const designName = document.getElementById("designName").value;
    const quantity = parseInt(document.getElementById("quantity").value);
    const currentCost = parseFloat(document.getElementById("currentCost").value);
    const sizeName = document.getElementById("sizeName").value;

    if (!designName || !quantity || !currentCost || !sizeName) {
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
        currentCost: currentCost,
        designName: designName,
        size: {
            sizeName: sizeName,
        },
    };

    try {
        const response = await fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productData),
        });

        if (response.status >= 200) {
            alert("Product created successfully!");
            document.getElementById("designName").value = "";
            document.getElementById("quantity").value = "";
            document.getElementById("currentCost").value = "";
            document.getElementById("sizeName").value = "";
        } else {
            alert("Failed to create the product. Please try again.");
        }
    } catch (error) {
        alert("An error occurred while sending the request.");
        console.error(error);
    }
}

const apiEndpoint = 'http://localhost:4040/api/product-list';
