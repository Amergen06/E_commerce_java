const LOGIN_URL = "http://localhost:8080/api/auth/login";

async function loginUser() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch(LOGIN_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    });

    const result = await response.text();

    if (response.ok) {
        alert(result);
        window.location.href = result.includes("Admin") ? "admin.html" : "shopper.html";
    } else {
        alert("Invalid credentials.");
    }
}
