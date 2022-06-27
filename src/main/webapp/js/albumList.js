/**
 * view-controller for albumlist.html
 * @author Hermann Witte
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {

    readAlbums();

});

/**
 * reads all songs
 */
function readAlbums() {
    let url = "./resource/album/list";
    fetch(url, {
        headers: {"Authorization": "Bearer " + readStorage("token")}
    })
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
                document.getElementById("albumlist").innerHTML = "Sie sind nicht berechtigt, diese Seite zu sehen!";
            }
        })
        .then(response => response.json())
        .then(data => {
            showAlbumlist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}
/**
 * shows the albumlist as a table
 * @param data  the songs
 */
function showAlbumlist(data) {
    const userRole = getCookie("userRole");
    let tBody = document.getElementById("albumlist");
    tBody.innerHTML = "";
    data.forEach(album => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";
        button.type = "button";
        button.name = "editalbum";
        button.setAttribute("data-albumuuid", album.albumUUID);
        button.addEventListener("click", editAlbum);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = album.title;
        row.insertCell(-1).innerHTML = album.artist.artistName;
        row.insertCell(-1).innerHTML = album.releaseDate;

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteAlbum";
            button.setAttribute("data-albumuuid", album.albumUUID);
            button.addEventListener("click", deleteAlbum);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./albumedit.html'>Neues Album</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editAlbum(event) {
    const button = event.target;
    const albumUUID = button.getAttribute("data-albumuuid");
    window.location.href = "./albumedit.html?uuid=" + albumUUID;
}

/**
 * deletes a song
 * @param event  the click-event
 */
function deleteAlbum(event) {
    const button = event.target;
    const albumUUID = button.getAttribute("data-albumuuid");

    fetch("./resource/album/delete?uuid=" + albumUUID,
        {
            method: "DELETE",
            headers: {"Authorization": "Bearer " + readStorage("token")}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./albumlist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}