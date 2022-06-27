/**
 * view-controller for musiccollection.html
 * @author Hermann Witte
 */

document.addEventListener("DOMContentLoaded", () => {

    readSongs();
});

/**
 * reads all songs
 */
function readSongs() {
    let url = "./resource/song/list";
    fetch(url, {
        headers: {"Authorization": "Bearer " + readStorage("token")}
    })
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
                document.getElementById("songlist").innerHTML = "Sie sind nicht berechtigt, diese Seite zu sehen!";
            }
        })
        .then(response => response.json())
        .then(data => {
            showSonglist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}
/**
 * shows the songlist as a table
 * @param data  the songs
 */
function showSonglist(data) {
    const userRole = getCookie("userRole");
    let tBody = document.getElementById("songlist");
    tBody.innerHTML = "";
    data.forEach(song => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";
        button.type = "button";
        button.name = "editSong";
        button.setAttribute("data-songuuid", song.songUUID);
        button.addEventListener("click", editSong);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = song.title;
        row.insertCell(-1).innerHTML = song.length;

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteSong";
            button.setAttribute("data-songuuid", song.songUUID);
            button.addEventListener("click", deleteSong);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./songedit.html'>Neuer Song</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editSong(event) {
    const button = event.target;
    const songUUID = button.getAttribute("data-songuuid");
    window.location.href = "./songedit.html?uuid=" + songUUID;
}

/**
 * deletes a song
 * @param event  the click-event
 */
function deleteSong(event) {
    const button = event.target;
    const songUUID = button.getAttribute("data-songuuid");

    fetch("./resource/song/delete?uuid=" + songUUID,
        {
            method: "DELETE",
            headers: {"Authorization": "Bearer " + readStorage("token")}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./musicCollection.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}
