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
