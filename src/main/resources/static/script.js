const amountInput = document.getElementById("amount");
const fromSelect = document.getElementById("from");
const toSelect = document.getElementById("to");
const resultInput = document.getElementById("result");
const authSection = document.getElementById("auth-section");

const rateElement = document.getElementById("rate");
const messageElement = document.getElementById("message");
const historyElement = document.getElementById("history");

const usernameInput = document.getElementById("username");
const passwordInput = document.getElementById("password");
const authMessage = document.getElementById("auth-message");
const exchangeButton = document.getElementById("exchange");

const loginButton = document.getElementById("login");
const registerButton = document.getElementById("register");
const userPanel = document.getElementById("user-panel");
const usernameDisplay = document.getElementById("username-display");
const logoutButton = document.getElementById("logout");


// ==============================
// ПРОВЕРКА ЭЛЕМЕНТОВ
// ==============================

console.log("script.js загружен");

console.log("login:", loginButton);
console.log("register:", registerButton);
console.log("username:", usernameInput);
console.log("password:", passwordInput);


// ==============================
// РЕГИСТРАЦИЯ
// ==============================

registerButton.addEventListener("click", async () => {

    const username = usernameInput.value.trim();
    const password = passwordInput.value;

    if (!username || !password) {
        authMessage.textContent =
            "Введите имя пользователя и пароль";
        return;
    }

    if (password.length < 6) {
        authMessage.textContent =
            "Пароль должен содержать минимум 6 символов";
        return;
    }

    try {

        const response = await fetch("/api/auth/register", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const text = await response.text();

        let data;

        try {
            data = JSON.parse(text);
        } catch {
            data = {};
        }

        console.log("Регистрация:", response.status, data);

        if (!response.ok) {

            authMessage.textContent =
                data.message ||
                data.error ||
                text ||
                "Ошибка регистрации";

            return;
        }

        authMessage.textContent =
            "Регистрация успешна. Теперь войдите.";

        passwordInput.value = "";

    } catch (error) {

        console.error("Ошибка регистрации:", error);

        authMessage.textContent =
            "Ошибка соединения с сервером";
    }
});


// ==============================
// ВХОД
// ==============================

loginButton.addEventListener("click", async () => {

    const username = usernameInput.value.trim();
    const password = passwordInput.value;

    if (!username || !password) {

        authMessage.textContent =
            "Введите имя пользователя и пароль";

        return;
    }

    try {

        const response = await fetch("/api/auth/login", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const text = await response.text();

        let data;

        try {
            data = JSON.parse(text);
        } catch {
            data = {};
        }

        console.log("Вход:", response.status, data);

        if (!response.ok) {

            authMessage.textContent =
                data.message ||
                data.error ||
                text ||
                "Ошибка входа";

            return;
        }

        localStorage.setItem("token", data.token);

        localStorage.setItem("username", data.username);

        authMessage.textContent =
            "Вы успешно вошли";

        passwordInput.value = "";

        updateAuthUI();

        await loadHistory();

    } catch (error) {

        console.error("Ошибка входа:", error);

        authMessage.textContent =
            "Ошибка соединения с сервером";
    }
});

// ==============================
// СОСТОЯНИЕ АВТОРИЗАЦИИ
// ==============================

function updateAuthUI() {

    const token = localStorage.getItem("token");
    const username = localStorage.getItem("username");

    if (token) {

        userPanel.style.display = "flex";

        usernameDisplay.textContent =
            username || "Пользователь";

        authSection.style.display = "none";

        exchangeButton.disabled = false;

    } else {

        userPanel.style.display = "none";

        usernameDisplay.textContent = "";

        authSection.style.display = "block";

        exchangeButton.disabled = true;
    }
}

// ==============================
// ПРОВЕРКА АВТОРИЗАЦИИ
// ==============================

function handleUnauthorized() {

    localStorage.removeItem("token");
    localStorage.removeItem("username");

    updateAuthUI();

    historyElement.textContent =
        "Сессия истекла. Войдите снова.";

    messageElement.textContent =
        "Сессия истекла. Войдите снова.";

    resultInput.value = "";

    console.log("JWT недействителен или истёк");
}

// ==============================
// ВЫХОД
// ==============================

logoutButton.addEventListener("click", () => {

    localStorage.removeItem("token");
    localStorage.removeItem("username");

    updateAuthUI();

    historyElement.textContent =
        "Войдите, чтобы увидеть историю";

    messageElement.textContent =
        "";

    resultInput.value = "";

    console.log("Пользователь вышел");
});


// ==============================
// КУРС
// ==============================

async function loadRate() {

    try {

        const response = await fetch("/api/rates");

        if (!response.ok) {
            throw new Error("Ошибка загрузки курса");
        }

        const data = await response.json();

        rateElement.textContent =
            `1 ${data.from} = ${data.rate} ${data.to}`;

    } catch (error) {

        console.error(error);

        rateElement.textContent =
            "Не удалось загрузить курс";
    }
}


// ==============================
// РАСЧЁТ
// ==============================

async function calculate() {

    const amount = Number(amountInput.value);

    if (!amount || amount <= 0) {

        resultInput.value = "";

        return;
    }

    try {

        const response = await fetch(
            "/api/exchange/calculate",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    from: fromSelect.value,
                    to: toSelect.value,
                    amount: amount
                })
            }
        );

        const data = await response.json();

        if (!response.ok) {

            resultInput.value = "";

            console.error(data);

            return;
        }

        resultInput.value =
            Number(data.result).toFixed(2);

    } catch (error) {

        console.error(
            "Ошибка расчёта:",
            error
        );
    }
}


// ==============================
// ПЕРЕКЛЮЧЕНИЕ ВАЛЮТ
// ==============================

document
    .getElementById("swap")
    .addEventListener("click", () => {

        const oldFrom = fromSelect.value;

        fromSelect.value = toSelect.value;

        toSelect.value = oldFrom;

        calculate();
    });


// ==============================
// ВВОД СУММЫ
// ==============================

amountInput.addEventListener(
    "input",
    calculate
);


// ==============================
// ИЗМЕНЕНИЕ ВАЛЮТ
// ==============================

fromSelect.addEventListener(
    "change",
    calculate
);

toSelect.addEventListener(
    "change",
    calculate
);


// ==============================
// ОБМЕН
// ==============================

document
    .getElementById("exchange")
    .addEventListener(
        "click",
        async () => {

            const amount =
                Number(amountInput.value);

            if (!amount || amount <= 0) {

                messageElement.textContent =
                    "Введите сумму больше нуля";

                return;
            }

            const token =
                localStorage.getItem("token");

            if (!token) {

                messageElement.textContent =
                    "Для обмена необходимо войти в аккаунт";

                return;
            }

            try {

                const response =
                    await fetch("/api/exchange", {

                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json",

                            "Authorization":
                                "Bearer " + token
                        },

                        body: JSON.stringify({
                            from: fromSelect.value,
                            to: toSelect.value,
                            amount: amount
                        })
                    });

                const data =
                    await response.json();

                if (!response.ok) {

                    if (response.status === 401) {
                        handleUnauthorized();
                        return;
                    }

                    messageElement.textContent =
                        data.message ||
                        data.error ||
                        "Ошибка обмена";

                    return;
                }

                resultInput.value =
                    Number(data.result).toFixed(2);

                messageElement.textContent =
                    "Обмен выполнен успешно";

                await loadHistory();

            } catch (error) {

                console.error(error);

                messageElement.textContent =
                    "Ошибка соединения с сервером";
            }
        }
    );


// ==============================
// ИСТОРИЯ
// ==============================

async function loadHistory() {

    const token = localStorage.getItem("token");

    if (!token) {
        historyElement.textContent =
            "Войдите, чтобы увидеть историю";
        return;
    }

    try {

        const response = await fetch("/api/exchange", {

            headers: {
                "Authorization": "Bearer " + token
            }
        });

        const data = await response.json();

        if (!response.ok) {

            if (response.status === 401) {
                handleUnauthorized();
                return;
            }

            historyElement.textContent =
                "Не удалось загрузить историю";

            return;
        }

        historyElement.innerHTML = "";

        if (data.length === 0) {

            historyElement.textContent =
                "История операций пуста";

            return;
        }

        data.forEach(exchange => {

            const item = document.createElement("div");

            item.className = "history-item";

            const date = new Date(exchange.createdAt);

            const formattedDate =
                date.toLocaleString("ru-RU", {
                    day: "2-digit",
                    month: "2-digit",
                    year: "numeric",
                    hour: "2-digit",
                    minute: "2-digit"
                });

            const amount =
                Number(exchange.amount).toLocaleString("ru-RU", {
                    maximumFractionDigits: 2
                });

            const result =
                Number(exchange.result).toLocaleString("ru-RU", {
                    maximumFractionDigits: 2
                });

            item.innerHTML = `
                <strong>
                    ${amount} ${exchange.fromCurrency}
                    →
                    ${result} ${exchange.toCurrency}
                </strong>

                <br>

                Курс: ${Number(exchange.rate).toFixed(2)}

                <br>

                <small>
                    ${formattedDate}
                </small>
            `;

            historyElement.appendChild(item);
        });

    } catch (error) {

        console.error(error);

        historyElement.textContent =
            "Не удалось загрузить историю";
    }
}


// ==============================
// ЗАПУСК
// ==============================

updateAuthUI();
loadRate();
loadHistory();

setInterval(() => {
    loadRate();
}, 60000);