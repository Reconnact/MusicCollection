/**
 * utility functions for multiple pages
 *
 * @author  Hermann Witte
 * @since   2022-06-26
 * @version 1.0
 */

/**
 * get the value of an url parameter identified by key
 * source: https://www.sitepoint.com/get-url-parameters-with-javascript/
 * @param key  the key to be searched
 * @returns values as a String or null
 */
function getQueryParam (key) {
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);

    return urlParams.get(key);
}

/**
 * shows an information or error message
 * @param text  the message text
 * @param type  the message type (info, warning, error)
 */
function showMessage(text, type) {
    const field = document.getElementById("message");
    field.className = type;
    field.innerText = text;
}

/**
 * gets the value of the cookie with the specified name
 * Source: https://www.w3schools.com/js/js_cookies.asp
 * @param cname  the name of the cookie
 * @returns {string}
 */
function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let cookieArray = decodedCookie.split(';');
    for(let i = 0; i <cookieArray.length; i++) {
        let cookie = cookieArray[i];
        while (cookie.charAt(0) == ' ') {
            cookie = cookie.substring(1);
        }
        if (cookie.indexOf(name) == 0) {
            return cookie.substring(name.length, cookie.length);
        }
    }
    return "";
}

/**
 * saves the JWToken in SessionStorage
 * @param headers  response headers
 */
function saveToken(headers) {
    const auth = headers.get("Authorization");
    const token = auth.substring(7);
    sessionStorage.setItem("token", token);
}

/**
 * reads the JWToken from SessionStorage
 * @returns {string}
 */
function readStorage(item) {
    let data = sessionStorage.getItem(item);
    return data;
}

/**
 * locks/unlocks a form: input=readonly, select=disabled / submit, reset-buttons = hidden
 * @param formId  the id of the form
 * @param locked  should the fields be locked or unlocked
 */
function lockForm(formId, locked=true) {
    const form = document.getElementById(formId);
    const inputs = form.getElementsByTagName("input");
    for (let i=0; i<inputs.length; i++) {
        inputs[i].readOnly = locked;
    }

    const selects = form.getElementsByTagName("select");
    for (let i=0; i<selects.length; i++) {
        selects[i].disabled = locked;
    }

    const buttons = form.querySelectorAll("button[type='submit'], button[type='reset']");
    for (let i=0; i<buttons.length; i++) {
        buttons[i].hidden = locked;
    }
}