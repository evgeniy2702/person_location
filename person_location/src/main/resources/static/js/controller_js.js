// PERSON BY LASTNAME

// Get the modal
var modalLastname = document.getElementById("modalLastname");

// Get the button that opens the modal
var btnLastname = document.getElementById("btnLastname");

// Get the <span> element that closes the modal
var spanLastname = document.getElementsByClassName("close")[0];

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
var modalDate = document.getElementById("modalDate");

// Get the button that opens the modal
var btnDate = document.getElementById("btnDate");

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

// // PERSON BY REFUGEE and VOCATION
//
// // Get the modal
// var modalFilter = document.getElementById("modalFilter");
//
// // Get the button that opens the modal
// var btnFilter = document.getElementById("btnFilter");
//
// // Get the <span> element that closes the modal
// var spanFilter = document.getElementsByClassName("close")[2];
//
// // When the user clicks the button, open the modal
// btnFilter.onclick = function() {
//         modalFilter.style.display = "block";
// }
//
// // When the user clicks on <span> (x), close the modal
// spanFilter.onclick = function() {
//     modalFilter.style.display = "none";
// }
//
// // When the user clicks anywhere outside of the modal, close it
// window.onclick = function(event) {
//     if (event.target === modalFilter) {
//         modalFilter.style.display = "none";
//     }
// };

// PERSON BY FHONE
// Get the modal
var modalPhone = document.getElementById("modalPhone");

// Get the button that opens the modal
var btnPhone = document.getElementById("btnPhone");

// Get the <span> element that closes the modal
var spanPhone = document.getElementsByClassName("close")[2];

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


// GOOGLE MAP MODAL WINDOW


    var allThOfGeolocation = document.querySelectorAll("[class='pl-md-5 th_geolocation']");

    for (var i = 0; i < allThOfGeolocation.length; i++) {


        // Get <div> Модальном окно and set id = "modalGoogleMap_1"
        let modelGoogleId = "modalGoogleMap_" + i;
        allThOfGeolocation[i].children.item(1).setAttribute("id", modelGoogleId);
        let modelGoogle = allThOfGeolocation[i].children.item(1);

        // Get the <a> that opens the modal and set id = "btnGoogleMap_1"
        let btnGoogleId = "btnGoogleMap_" + i;
        let hrefGoogleHref = "#modalGoogleMap_" + i;
        allThOfGeolocation[i].children.item(0).setAttribute("id",  btnGoogleId);
        allThOfGeolocation[i].children.item(0).setAttribute("href",hrefGoogleHref);
        let btnGoogle = allThOfGeolocation[i].children.item(0);


        // Get the <span> element that closes the modal
        let spanGoogleId = "spanGoogleMap_" + i;
        allThOfGeolocation[i].children.item(1).children.item(0).children.item(0).setAttribute("id", spanGoogleId);
        let spanGoogle = allThOfGeolocation[i].children.item(1).children.item(0).children.item(0);

        // When the user clicks the button, open the modal
        btnGoogle.onclick = function(){
            modelGoogle.style.display = "block";
        };

        // When the user clicks on <span> (x), close the modal
        spanGoogle.onclick = function(){
            modelGoogle.style.display = "none";
        };

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
            if (event.target === modelGoogle) {
                modelGoogle.style.display = "none";
            }
        };
}


// INPUT PHONE NUMBER ACCORDING EXAMPLE 067-222-22-22

let input = document.getElementById('tel');

input.addEventListener("keydown", (event) => {
    var text = event.target.value;
    console.log(event.code + " " + event.key);
    if (event.key !== "Enter") {
        if (text.length === 3 || text.length === 7 || text.length === 10) {
            if (event.key === "Delete" || event.key === "Backspace") {
                text = event.target.value.delete("-");
            } else {
                text = event.target.value.concat("-");
            }

            input.value = text;
            console.log(text + " " + input);
        }
        if (text.length >= 13) {
            event.target.value.concat('');
        }
    }

});