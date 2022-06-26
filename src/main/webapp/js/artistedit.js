/**
 * view-controller for artistedit.html
 * @author Marcel Suter
 */
document.addEventListener("DOMContentLoaded", () => {
    readArtist();

    document.getElementById("artisteditFomr").addEventListener("submit", saveArtist);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of an artist
 */
function saveArtist(event) {
    event.preventDefault();
    showMessage("", "info");

    const artistForm = document.getElementById("artisteditFomr");
    const formData = new FormData(artistForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/artist/";
    const artistUUID = getQueryParam("uuid");
    if (artistUUID == null) {
        method = "POST";
        url += "create";
    } else {
        method = "PUT";
        url += "update";
    }

    fetch(url,
        {
            method: method,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Authorization": "Bearer " + readStorage("token")
            },
            body: data
        })
        .then(function (response) {
            if (!response.ok) {
                showMessage("Fehler beim Speichern", "error");
                console.log(response);
            } else {
                showMessage("Musiker gespeichert", "info");
                return response;}
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads an artist
 */
function readArtist() {
    const artistUUID = getQueryParam("uuid");
    fetch("./resource/artist/read?uuid=" + artistUUID, {
        headers: { "Authorization": "Bearer " + readStorage("token")}
    })
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
            }
        })
        .then(response => response.json())
        .then(data => {
            showArtist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of an artist
 * @param data  the artist-data
 */
function showArtist(data) {
    const userRole = getCookie("userRole");
    document.getElementById("artistUUID").value = data.artistUUID;
    document.getElementById("lastName").value = data.lastName;
    document.getElementById("firstName").value = data.firstName;
    document.getElementById("artistName").value = data.artistName;
    document.getElementById("birthday").value = data.birthday;

    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("artisteditForm", locked);
}

/**
 * redirects to the musiccollection
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./musicCollection.html";
}