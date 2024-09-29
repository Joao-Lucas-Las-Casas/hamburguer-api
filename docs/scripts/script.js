const createUser = (event) => {
    event.preventDefault();

    const form = document.getElementById('register-form');
    const formData = new FormData(form);

    const data = {};
    formData.forEach((value, key) => {
        data[key] = value;
    });

    fetch('https://webhook.site/16025872-bf59-4bdf-90ba-358ae674b586', {
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