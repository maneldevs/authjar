document.addEventListener("DOMContentLoaded", function () {
    var button = document.getElementById("generateApiKeyBtn");
    var input = document.getElementById("apiKey");

    if (!button || !input) {
        return;
    }

    button.addEventListener("click", function () {
        fetch(button.getAttribute("data-generate-url"), { method: "POST" })
            .then(function (response) { return response.json(); })
            .then(function (data) {
                input.value = data.apiKey;
            });
    });
});
