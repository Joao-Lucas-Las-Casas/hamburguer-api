const sendForm = async (event, url, method) => {
    event.preventDefault();
    const form = document.getElementById(event.target.id);

    const formData = new FormData(form);

    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    const response = await fetch(`http://localhost:8080${url}`, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        throw new Error(`Erro na requisição: ${response.status}`);
    }

    return await response.json();
};

const login = async (event) => {
    const response = await sendForm(event, '/v1/login', 'POST');
    const token = response.token;
    localStorage.setItem(token, token);
}

