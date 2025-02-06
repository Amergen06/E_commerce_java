const API_URL = "http://localhost:8080/api/products";

// Load product details from local storage
async function loadOrderDetails() {
    const productId = localStorage.getItem("selectedProduct");

    if (!productId) {
        document.getElementById("order-details").innerHTML = "<p>No product selected.</p>";
        return;
    }

    try {
        const response = await fetch(`${API_URL}/${productId}`);
        const product = await response.json();

        document.getElementById("order-details").innerHTML = `
            <p><strong>Product:</strong> ${product.name}</p>
            <p><strong>Price:</strong> $${product.price}</p>
            <p><strong>Quantity:</strong> 1</p>
        `;
    } catch (error) {
        console.error("Error loading product details:", error);
        document.getElementById("order-details").innerHTML = "<p>Error loading product details.</p>";
    }
}

// Function to confirm order
function confirmOrder() {
    alert("Order Confirmed! Thank you for your purchase.");
    localStorage.removeItem("selectedProduct"); // Clear selected product after order
    window.location.href = "index.html"; // Redirect back to home page
}

// Load product details on page load
document.addEventListener("DOMContentLoaded", loadOrderDetails);
