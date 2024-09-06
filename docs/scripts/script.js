const createUser = (event) => {
    event.preventDefault();

    const form = document.getElementById('register-form');
    const formData = new FormData(form);

    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    fetch('https://webhook.site/394c5c86-fb80-43ba-9dc7-2cd4d5dd4ec1', {
        method: 'POST',
        mode: 'no-cors',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .catch((error) => console.log(error));
};