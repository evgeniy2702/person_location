// PERSON BY LASTNAME

// Get the modal
let modalLastname = document.getElementById("modalLastname");

// Get the button that opens the modal
let btnLastname = document.getElementById("btnLastname");

// Get the <span> element that closes the modal
let spanLastname = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal 
btnLastname.onclick = function() {
    modalLastname.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
spanLastname.onclick = function() {
    modalLastname.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modalLastname) {
        modalLastname.style.display = "none";
    }
};

// PERSON BY DATE
// Get the modal
let modalDate = document.getElementById("modalDate");

// Get the button that opens the modal
let btnDate = document.getElementById("btnDate");

// Get the <span> element that closes the modal
var spanDate = document.getElementsByClassName("close")[1];

// When the user clicks the button, open the modal
btnDate.onclick = function() {
    modalDate.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
spanDate.onclick = function() {
    modalDate.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modalDate) {
        modalDate.style.display = "none";
    }
};

// PERSON BY REFUGEE and VOCATION

// Get the modal
let modalFilter = document.getElementById("modalFilter");

// Get the button that opens the modal
let btnFilter = document.getElementById("btnFilter");

// Get the <span> element that closes the modal
let spanFilter = document.getElementsByClassName("close")[2];

// When the user clicks the button, open the modal
btnFilter.onclick = function() {
        modalFilter.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
spanFilter.onclick = function() {
    modalFilter.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modalFilter) {
        modalFilter.style.display = "none";
    }
};

// PERSON BY FHONE
// Get the modal
let modalPhone = document.getElementById("modalPhone");

// Get the button that opens the modal
let btnPhone = document.getElementById("btnPhone");

// Get the <span> element that closes the modal
let spanPhone = document.getElementsByClassName("close")[3];

// When the user clicks the button, open the modal
btnPhone.onclick = function() {
    modalPhone.style.display = "block";
};

// When the user clicks on <span> (x), close the modal
spanPhone.onclick = function() {
    modalPhone.style.display = "none";
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modalPhone) {
        modalPhone.style.display = "none";
    }
};