const API_URL = "http://localhost:8080/api/products";
const LOGIN_URL = "http://localhost:8080/api/auth/login";
const ORDER_URL = "http://localhost:8080/api/orders";


async function loadProducts() {
    try {
        const response = await fetch(API_URL);
        let products = await response.json();

        products.sort((a, b) => a.price - b.price);

        displayProducts(products);
    } catch (error) {
        console.error("Error loading products:", error);
    }
}

function displayProducts(products) {
    const list = document.getElementById("product-list");
    if (!list) {
        console.error("Product list element not found!");
        return;
    }

    list.innerHTML = "";

    products.forEach(product => {
        const item = document.createElement("li");
        item.innerHTML = `
            <strong>${product.name}</strong> - $${product.price}
            <button onclick="orderProduct(${product.id})">Buy</button>
        `;
        list.appendChild(item);
    });
}


async function loadAdminProducts() {
    try {
        const response = await fetch(API_URL);
        const products = await response.json();

        const list = document.getElementById("admin-product-list");
        if (!list) return;

        list.innerHTML = "";

        products.forEach(product => {
            const item = document.createElement("li");
            item.innerHTML = `
                ${product.name} - $${product.price}
                <button onclick="editProduct(${product.id})">Edit</button>
                <button onclick="deleteProduct(${product.id})">Delete</button>
            `;
            list.appendChild(item);
        });
    } catch (error) {
        console.error("Error loading admin products:", error);
    }
}

async function addProduct() {
    const name = document.getElementById("product-name").value;
    const price = document.getElementById("product-price").value;

    if (!name || !price) {
        alert("Please enter a name and price.");
        return;
    }

    const response = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, price })
    });

    if (response.ok) {
        alert("Product added successfully!");
        location.reload(); // Reload the page to refresh the product list
    } else {
        alert("Failed to add product. Please try again.");
    }
}


async function deleteProduct(id) {
    await fetch(`${API_URL}/${id}`, { method: "DELETE" });
    loadAdminProducts(); // Refresh list
}

async function editProduct(id) {
    const name = prompt("Enter new name:");
    const price = prompt("Enter new price:");

    if (!name || !price) return;

    const response = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, price })
    });

    if (response.ok) {
        loadAdminProducts();
    }
}


async function orderProduct(productId) {
    localStorage.setItem("selectedProduct", productId); // Store product ID in local storage
    window.location.href = "order.html"; // Redirect to the order page
}


async function processPayment() {
    alert("Processing payment...");
    setTimeout(() => {
        alert("Payment Successful!");
    }, 2000);
}

document.addEventListener("DOMContentLoaded", () => {
    const loginButton = document.getElementById("login-button");
    if (loginButton) {
        loginButton.addEventListener("click", loginUser);
    }
});

async function loginUser() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!email || !password) {
        showNotification("❌ Please enter both email and password.", "error");
        return;
    }

    try {
        const response = await fetch(LOGIN_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        const result = await response.json();

        if (response.ok) {
            showNotification("✅ Login successful! Redirecting...", "success");

            localStorage.setItem("token", result.token);

            setTimeout(() => {
                window.location.href = "index.html";
            }, 2000);
        } else {
            showNotification("❌ Invalid email or password.", "error");
        }
    } catch (error) {
        console.error("Error logging in:", error);
        showNotification("⚠️ An error occurred while logging in.", "error");
    }
}

function showNotification(message, type) {
    const notification = document.getElementById("notification");
    notification.textContent = message;
    notification.className = type;

    setTimeout(() => {
        notification.textContent = "";
        notification.className = "";
    }, 3000);
}


document.addEventListener("DOMContentLoaded", () => {
    const loginButton = document.getElementById("login-button");
    if (loginButton) {
        loginButton.addEventListener("click", loginUser);
    }
});


async function searchProducts() {
    const searchTerm = document.getElementById("search-box").value.trim();

    if (!searchTerm) {
        loadProducts();
        return;
    }

    try {
        const response = await fetch(`${API_URL}/search?name=${encodeURIComponent(searchTerm)}`);
        const products = await response.json();
        displayProducts(products);
    } catch (error) {
        console.error("Error searching for products:", error);
    }
}



document.addEventListener("DOMContentLoaded", () => {
    loadProducts();

    if (document.getElementById("admin-product-list")) {
        loadAdminProducts();
    }
});


