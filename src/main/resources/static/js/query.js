document.addEventListener("DOMContentLoaded", function () {
    var queryForm = document.getElementById("queryForm");
    queryForm.addEventListener("submit", function (e) {
        e.preventDefault();
        var query = queryForm.querySelector("textarea[name='query']").value;

        fetch('/query', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ query: query })
        }).then(response => response.json())
            .then(data => {
                document.getElementById("responseArea").innerText = data.response;
            }).catch(error => {
            console.error('Error:', error);
        });
    });
});
