/**
 * view-controller for artistlist.html
 * @author Hermann Witte
 */
let delayTimer;

document.addEventListener("DOMContentLoaded", () => {

    readArtists();

});

/**
 * reads all songs
 */
function readArtists() {
    let url = "./resource/artist/list";
    fetch(url, {
        headers: {"Authorization": "Bearer " + readStorage("token")}
    })
        .then(function (response) {
            if (response.ok) {
                return response;
            } else {
                console.log(response);
                document.getElementById("artistlist").innerHTML = "Sie sind nicht berechtigt, diese Seite zu sehen!";
            }
        })
        .then(response => response.json())
        .then(data => {
            showArtistlist(data);
        })
        .catch(function (error) {
            console.log(error);
        });
}
/**
 * shows the artistlist as a table
 * @param data  the songs
 */
function showArtistlist(data) {
    const userRole = getCookie("userRole");
    let tBody = document.getElementById("artistlist");
    tBody.innerHTML = "";
    data.forEach(artist => {
        let row = tBody.insertRow(-1);

        let button = document.createElement("button");
        if (userRole === "admin")
            button.innerHTML = "&#9998;";
        else
            button.innerHTML = "&#128065;";
        button.type = "button";
        button.name = "editartist";
        button.setAttribute("data-artistuuid", artist.artistUUID);
        button.addEventListener("click", editArtist);
        row.insertCell(-1).appendChild(button);

        row.insertCell(-1).innerHTML = artist.firstName;
        row.insertCell(-1).innerHTML = artist.lastName;
        row.insertCell(-1).innerHTML = artist.artistName;
        row.insertCell(-1).innerHTML = artist.birthday;

        if (userRole === "admin") {
            button = document.createElement("button");
            button.innerHTML = "&#128465;";
            button.type = "button";
            button.name = "deleteArtist";
            button.setAttribute("data-artistuuid", artist.artistUUID);
            button.addEventListener("click", deleteArtist);
            row.insertCell(-1).appendChild(button);
        }

    });

    if (userRole === "admin") {
        document.getElementById("addButton").innerHTML = "<a href='./artistedit.html'>Neuer Musiker</a>";
    }
}

/**
 * redirects to the edit-form
 * @param event  the click-event
 */
function editArtist(event) {
    const button = event.target;
    const artistUUID = button.getAttribute("data-artistuuid");
    window.location.href = "./artistedit.html?uuid=" + artistUUID;
}

/**
 * deletes a song
 * @param event  the click-event
 */
function deleteArtist(event) {
    const button = event.target;
    const artistUUID = button.getAttribute("data-artistuuid");

    fetch("./resource/artist/delete?uuid=" + artistUUID,
        {
            method: "DELETE",
            headers: {"Authorization": "Bearer " + readStorage("token")}
        })
        .then(function (response) {
            if (response.ok) {
                window.location.href = "./artistlist.html";
            } else {
                console.log(response);
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}