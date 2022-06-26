/**
 * view-controller for album.html
 * @author Marcel Suter
 */
document.addEventListener("DOMContentLoaded", () => {
    readSongs();
    readArtists();
    readAlbum();

    document.getElementById("albumeditForm").addEventListener("submit", saveAlbum);
    document.getElementById("cancel").addEventListener("click", cancelEdit);
});

/**
 * saves the data of an album
 */
function saveAlbum(event) {
    event.preventDefault();
    showMessage("", "info");

    const albumForm = document.getElementById("albumeditForm");
    const formData = new FormData(albumForm);
    const data = new URLSearchParams(formData);

    let method;
    let url = "./resource/album/";
    const albumUUID = getQueryParam("uuid");
    if (albumUUID == null) {
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
                showMessage("Album gespeichert", "info");
                return response;}
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * reads an album
 */
function readAlbum() {
    const albumUUID = getQueryParam("uuid");
    fetch("./resource/album/read?uuid=" + albumUUID, {
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
            showAlbum(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * show the data of a album
 * @param data  the album-data
 */
function showAlbum(data) {
    const userRole = getCookie("userRole");
    document.getElementById("albumUUID").value = data.albumUUID;
    document.getElementById("title").value = data.title;
    document.getElementById("releaseDate").value = data.releaseDate;
    document.getElementById("artist").value = data.artistUUID;

    selectedSongs(data.songList)
    const locked =  !(userRole === "user" || userRole === "admin");
    lockForm("songeditForm", locked);
}



/**
 * reads all arists as an array
 */
function readArtists() {

    fetch("./resource/artist/list", {
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
            showArtists(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all artists as a dropdown
 * @param data
 */
function showArtists(data) {
    let dropdown = document.getElementById("artist");
    data.forEach(artist => {
        let option = document.createElement("option");
        option.text = artist.artistName;
        option.value = artist.artistUUID;
        dropdown.add(option);
    })
}

/**
 * reads all songs as an array
 */
function readSongs() {

    fetch("./resource/song/list", {
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
            showSongs(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}

/**
 * shows all songs as a dropdown
 * @param data
 */
function showSongs(data) {
    let dropdown = document.getElementById("song");
    data.forEach(song => {
        let option = document.createElement("option");
        option.id = song.songUUID;
        option.text = song.title;
        option.value = song.songUUID;
        dropdown.add(option);
    })
}

function selectedSongs(songlist) {
    songlist.forEach(song => {
        document.getElementById(song.songUUID).selected = true;
    })
}

/**
 * redirects to the musiccollection
 * @param event  the click-event
 */
function cancelEdit(event) {
    window.location.href = "./musicCollection.html";
}
