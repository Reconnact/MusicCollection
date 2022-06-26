/**
 * view-controller for songedit.html
 * @author Marcel Suter
 */
document.addEventListener("DOMContentLoaded", () => {
    readSong();

    document.getElementById("songeditForm").addEventListener("submit", saveSong);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of a song
 */
function saveSong(event) {
    event.preventDefault();
    showMessage("", "info");

    const songForm = document.getElementById("songeditForm");
    const formData = new FormData(songForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/song/";
    const songUUID = getQueryParam("uuid");
    if (songUUID == null) {
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
                showMessage("Song gespeichert", "info");
                return response;}
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads a song
 */
function readSong() {
    const songUUID = getQueryParam("uuid");
    fetch("./resource/song/read?uuid=" + songUUID, {
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
            showSong(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a song
 * @param data  the song-data
 */
function showSong(data) {
    const userRole = getCookie("userRole");
    document.getElementById("songUUID").value = data.songUUID;
    document.getElementById("title").value = data.title;
    document.getElementById("length").value = data.length;

    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("songeditForm", locked);
}

/**
 * redirects to the musiccollection
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./musicCollection.html";
}