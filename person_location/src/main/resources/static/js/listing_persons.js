// LISTING PERSONS ON PAGE BY 20 ROWS BY CLICK BUTTON


// HEADER DIV BUTTONS

let divHeader = document.getElementById("header");

let buttonTopMinusNew = "<a id='buttonTopMinus' class='btn btn-outline-success' href='#' style='pointer-events: none;'> - 20 </a>";
let buttonTopMinusNewTag = document.createElement("a");
buttonTopMinusNewTag.innerHTML = buttonTopMinusNew;
divHeader.children[0].replaceWith(buttonTopMinusNewTag);

let buttonTopPlusNew = "<a id='buttonTopPlus' class='btn btn-outline-success' href='#' > + 20 </a>";
let buttonTopPlusNewTag = document.createElement("a");
buttonTopPlusNewTag.innerHTML = buttonTopPlusNew;
divHeader.children[4].replaceWith(buttonTopPlusNewTag);

let buttonTopPlus = document.getElementById("buttonTopPlus");
let buttonTopMinus = document.getElementById("buttonTopMinus");

// FOOTER DIV BUTTONS

let divFooter = document.getElementById("footer");

let buttonBottomMinusNew = "<a id='buttonBottomMinus' class='btn btn-outline-success' href='#' style='pointer-events: none;'> - 20 </a>";
let buttonBottomMinusNewTag = document.createElement("a");
buttonBottomMinusNewTag.innerHTML = buttonBottomMinusNew;
divFooter.children[0].replaceWith(buttonBottomMinusNewTag);

let buttonBottomPlusNew = "<a id='buttonBottomPlus' class='btn btn-outline-success' href='#' > + 20 </a>";
let buttonBottomPlusNewTag = document.createElement("a");
buttonBottomPlusNewTag.innerHTML = buttonBottomPlusNew;
divFooter.children[2].replaceWith(buttonBottomPlusNewTag);

let buttonBottomMinus = document.getElementById("buttonBottomMinus");
let buttonBottomPlus = document.getElementById("buttonBottomPlus");


// BLOCK BUTTONS "+ 20" WHEN PARAM END > OR = PERSONS_LIST LENGTH

if(person_list.length <= end) {
    buttonBottomPlus.setAttribute("style", "pointer-events: none;");
    buttonTopPlus.setAttribute("style", "pointer-events: none;");
}



// SET UP FUNCTIONS TO THE BUTTONS ON HEADER DIV

buttonTopPlus.onclick = function () {
    start += 20;
    end += 20;

    if(end > person_list.length)
        end = person_list.length - 1;

    console.log(start + " / " + end);

    let tbody = document.getElementsByTagName("tbody")[1];

    let count = 0;

    buttonTopMinus.removeAttribute("style");
    buttonBottomMinus.removeAttribute("style");

    if(end < person_list.length ){

        tbody.innerHTML = "";

        for (let i = start ; i <= end ; i++) {

            let btnGoogleMap = "btnGoogleMap_" + i;
            let modalGoogleMap = "modalGoogleMap_" + i;
            let spanGoogleMap = "spanGoogleMap_" + i;

            let vacation = person_list[i].vacation ? 'так' : 'ні';
            console.log(vacation);
            let refuge = person_list[i].refugee ? 'так' : 'ні';
            console.log(refuge);
            let able_for_work = person_list[i].able_for_work ? 'так' : 'ні';
            console.log(able_for_work);
            let work_remote = person_list[i].work_remote ? 'віддалено' : '';
            console.log(work_remote);
            let work_by_place = person_list[i].work_by_place ? 'за місцем роботи' : '';
            console.log(work_by_place);
            let war_zone = person_list[i].war_zone ? '&nbsp;- на даний час це є зона бойових дій' : '&nbsp;- на даний час це не є зоною бойових дій';
            console.log(war_zone);

            let trString = "<tr class='pl-md-5' scope='row'><" +
                "th class='pl-md-5 th_geolocation' scope='row'>" +

                " <!-- Триггер / Открыть Модальный -->\n" +
                "                                    <a id='" + btnGoogleMap + "' href='#" + modalGoogleMap + "'>" + person_list[i].lastname + "</a>\n" +
                "\n" +
                "                                    <!-- Модальном окно -->\n" +
                "                                    <div id='" + modalGoogleMap + "' class='modal card-header div_modal'>\n" +
                "\n" +
                "                                        <!-- Модальное содержание -->\n" +
                "                                        <div class='modal-content'>\n" +
                "                                            <span id='" + spanGoogleMap + "' class='close'>&times;</span>\n" +
                "\n" +
                "                                            <footer>\n" +
                "                                                <div class='footerBox'>\n" +
                "\n" +
                "                                                    <div class='footerInfoBox'>\n" +
                "                                                        <div style='display: flex; flex-direction: row; flex-wrap: nowrap;'>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                                <h6 >У відпусці : </h6>\n" +
                "                                                                <p >" + vacation + "</p>\n" +
                "                                                            </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                                <h6 >Біженець : </h6>\n" +
                "                                                                <p >" + refuge + "</p>\n" +
                "                                                            </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                            <h6 >Може працювати : </h6>\n" +
                "                                                            <p >" + able_for_work + "</p>\n" +
                "                                                        </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;' >\n" +
                "                                                                <h6 >Працює : </h6>\n" +
                "                                                                <p >" + work_remote + "</p>\n" +
                "                                                                <p >" + work_by_place + "</p>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                        <div style='display: flex; flex-direction: row; padding-left: 10%; width: 90%;'>\n" +
                "                                                            <h6 >Регіон : &nbsp;</h6>\n" +
                "                                                            <p >\n" +
                "                                                                <span >" + person_list[i].state + "</span>\n" +
                "                                                                <span >" + war_zone + "</span>\n" +
                "                                                            </p>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "                                            </footer>\n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>" +

                "</th>" +
                "<td>" + person_list[i].firstname + "</td>" +
                "<td>" + person_list[i].middle_name + "</td>" +
                "<td>" + person_list[i].directorate + "</td>" +
                "<td>" + person_list[i].phone + "</td>" +
                "<td>" + person_list[i].date_add.split('T')[0] + "</td>" +
                "<th class='pl-md-5 th_geolocation' >" +

                "\n" +
                "                                    <!-- Триггер / Открыть Модальный -->\n" +
                "                                    <a id='" + btnGoogleMap + "' href='#" + modalGoogleMap + "' >" + person_list[i].text_location + "</a>\n" +
                "\n" +
                "                                    <!-- Модальном окно -->\n" +
                "                                    <div id='" + modalGoogleMap + "' class='modal card-header text-center div_modal'>\n" +
                "\n" +
                "                                        <!-- Модальное содержание -->\n" +
                "                                        <div class='modal-content map'>\n" +
                "                                            <span id='" + spanGoogleMap + "' class='close'>&times;</span>\n" +
                "\n" +
                "                                            <footer>\n" +
                "                                                <div class='footerBox'>\n" +
                "\n" +
                "                                                    <div class='footerInfoBox'>\n" +
                "                                                        <div class='googleMap'>\n" +
                "                                                            <!--<iframe th:src=\"@{'https://www.google.com/maps/embed/v1/place?key=AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s&q=' + ${person.getGeolocation()} + '&center=' + ${person.getGeolocation()} + '&zoom=10&maptype=roadmap&language=ru'}\" allowfullscreen=\"\" loading=\"lazy\"></iframe>-->\n" +
                "                                                            <iframe src=" + person_list[i].link_geolocation + " allowfullscreen='' loading='lazy'></iframe>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "                                            </footer>\n" +
                "\n" +
                "                                         </div>\n" +
                "                                     </div>\n" +

                "</th>" +
                "<td>" + person_list[i].last_modified.split('T')[0] + "</td>" +
                "</tr>";

            let row = tbody.insertRow(count);
            row.innerHTML = trString;
            count++;
        }

    }

    end = start + 19;

    if (end >= person_list.length - 1){
        buttonTopPlus.setAttribute("style" , "pointer-events: none;");
        buttonBottomPlus.setAttribute("style" , "pointer-events: none;");
    }


    // GOOGLE MAP MODAL WINDOW


    let allThOfGeolocation = document.querySelectorAll("[class='pl-md-5 th_geolocation']");

    for (let i = 0; i < allThOfGeolocation.length; i++) {


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

}
buttonTopMinus.onclick = function () {

    start -= 20;
    end -= 20;

    console.log(start + " / " + end);

    let tbody = document.getElementsByTagName("tbody")[1];

    let count = 0;

    buttonTopPlus.removeAttribute("style");
    buttonBottomPlus.removeAttribute("style");

    if(start >= 0) {

        tbody.innerHTML = "";

        for (let i = start ; i <= end ; i++) {

            let btnGoogleMap = "btnGoogleMap_" + i;
            let modalGoogleMap = "modalGoogleMap_" + i;
            let spanGoogleMap = "spanGoogleMap_" + i;
            let vacation = person_list[i].vacation ? 'так' : 'ні';
            console.log(vacation);
            let refuge = person_list[i].refugee ? 'так' : 'ні';
            console.log(refuge);
            let able_for_work = person_list[i].able_for_work ? 'так' : 'ні';
            console.log(able_for_work);
            let work_remote = person_list[i].work_remote ? 'віддалено' : '';
            console.log(work_remote);
            let work_by_place = person_list[i].work_by_place ? 'за місцем роботи' : '';
            console.log(work_by_place);
            let war_zone = person_list[i].war_zone ? '&nbsp;- на даний час це є зона бойових дій' : '&nbsp;- на даний час це не є зоною бойових дій';
            console.log(war_zone);

            let trString = "<tr class='pl-md-5' scope='row'><" +
                "th class='pl-md-5 th_geolocation' scope='row'>" +

                " <!-- Триггер / Открыть Модальный -->\n" +
                "                                    <a id='" + btnGoogleMap + "' href='#" + modalGoogleMap + "'>" + person_list[i].lastname + "</a>\n" +
                "\n" +
                "                                    <!-- Модальном окно -->\n" +
                "                                    <div id='" + modalGoogleMap + "' class='modal card-header div_modal'>\n" +
                "\n" +
                "                                        <!-- Модальное содержание -->\n" +
                "                                        <div class='modal-content'>\n" +
                "                                            <span id='" + spanGoogleMap + "' class='close'>&times;</span>\n" +
                "\n" +
                "                                            <footer>\n" +
                "                                                <div class='footerBox'>\n" +
                "\n" +
                "                                                    <div class='footerInfoBox'>\n" +
                "                                                        <div style='display: flex; flex-direction: row; flex-wrap: nowrap;'>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                                <h6 >У відпусці : </h6>\n" +
                "                                                                <p >" + vacation + "</p>\n" +
                "                                                            </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                                <h6 >Біженець : </h6>\n" +
                "                                                                <p >" + refuge + "</p>\n" +
                "                                                            </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                            <h6 >Може працювати : </h6>\n" +
                "                                                            <p >" + able_for_work + "</p>\n" +
                "                                                        </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;' >\n" +
                "                                                                <h6 >Працює : </h6>\n" +
                "                                                                <p >" + work_remote + "</p>\n" +
                "                                                                <p >" + work_by_place + "</p>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                        <div style='display: flex; flex-direction: row; padding-left: 10%; width: 90%;'>\n" +
                "                                                            <h6 >Регіон : &nbsp;</h6>\n" +
                "                                                            <p >\n" +
                "                                                                <span >" + person_list[i].state + "</span>\n" +
                "                                                                <span >" + war_zone + "</span>\n" +
                "                                                            </p>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "                                            </footer>\n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>" +

                "</th>" +
                "<td>" + person_list[i].firstname + "</td>" +
                "<td>" + person_list[i].middle_name + "</td>" +
                "<td>" + person_list[i].directorate + "</td>" +
                "<td>" + person_list[i].phone + "</td>" +
                "<td>" + person_list[i].date_add.split('T')[0] + "</td>" +
                "<th class='pl-md-5 th_geolocation' >" +

                "\n" +
                "                                    <!-- Триггер / Открыть Модальный -->\n" +
                "                                    <a id='" + btnGoogleMap + "' href='#" + modalGoogleMap + "' >" + person_list[i].text_location + "</a>\n" +
                "\n" +
                "                                    <!-- Модальном окно -->\n" +
                "                                    <div id='" + modalGoogleMap + "' class='modal card-header text-center div_modal'>\n" +
                "\n" +
                "                                        <!-- Модальное содержание -->\n" +
                "                                        <div class='modal-content map'>\n" +
                "                                            <span id='" + spanGoogleMap + "' class='close'>&times;</span>\n" +
                "\n" +
                "                                            <footer>\n" +
                "                                                <div class='footerBox'>\n" +
                "\n" +
                "                                                    <div class='footerInfoBox'>\n" +
                "                                                        <div class='googleMap'>\n" +
                "                                                            <!--<iframe th:src=\"@{'https://www.google.com/maps/embed/v1/place?key=AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s&q=' + ${person.getGeolocation()} + '&center=' + ${person.getGeolocation()} + '&zoom=10&maptype=roadmap&language=ru'}\" allowfullscreen=\"\" loading=\"lazy\"></iframe>-->\n" +
                "                                                            <iframe src=" + person_list[i].link_geolocation + " allowfullscreen='' loading='lazy'></iframe>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "                                            </footer>\n" +
                "\n" +
                "                                         </div>\n" +
                "                                     </div>\n" +

                "</th>" +
                "<td>" + person_list[i].last_modified.split('T')[0] + "</td>" +
                "</tr>";

            let row = tbody.insertRow(count);
            row.innerHTML = trString;
            count++;
        }
    }
    if(start <= 0){
        buttonTopMinus.setAttribute("style", "pointer-events: none;");
        buttonBottomMinus.setAttribute("style", "pointer-events: none;");
    }


    // GOOGLE MAP MODAL WINDOW

    let allThOfGeolocation = document.querySelectorAll("[class='pl-md-5 th_geolocation']");

    for (let i = 0; i < allThOfGeolocation.length; i++) {


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

}

// SET UP FUNCTIONS TO THE BUTTONS ON FOOTER DIV

buttonBottomMinus.onclick =function () {

    start -= 20;
    end -= 20;

    console.log(start + " / " + end);

    let tbody = document.getElementsByTagName("tbody")[1];

    let count = 0;

    buttonTopPlus.removeAttribute("style");
    buttonBottomPlus.removeAttribute("style");

    if(start >= 0) {

        tbody.innerHTML = "";

        for (let i = start ; i <= end ; i++) {

            let btnGoogleMap = "btnGoogleMap_" + i;
            let modalGoogleMap = "modalGoogleMap_" + i;
            let spanGoogleMap = "spanGoogleMap_" + i;
            let vacation = person_list[i].vacation ? 'так' : 'ні';
            console.log(vacation);
            let refuge = person_list[i].refugee ? 'так' : 'ні';
            console.log(refuge);
            let able_for_work = person_list[i].able_for_work ? 'так' : 'ні';
            console.log(able_for_work);
            let work_remote = person_list[i].work_remote ? 'віддалено' : '';
            console.log(work_remote);
            let work_by_place = person_list[i].work_by_place ? 'за місцем роботи' : '';
            console.log(work_by_place);
            let war_zone = person_list[i].war_zone ? '&nbsp;- на даний час це є зона бойових дій' : '&nbsp;- на даний час це не є зоною бойових дій';
            console.log(war_zone);

            let trString = "<tr class='pl-md-5' scope='row'><" +
                "th class='pl-md-5 th_geolocation' scope='row'>" +

                " <!-- Триггер / Открыть Модальный -->\n" +
                "                                    <a id='" + btnGoogleMap + "' href='#" + modalGoogleMap + "'>" + person_list[i].lastname + "</a>\n" +
                "\n" +
                "                                    <!-- Модальном окно -->\n" +
                "                                    <div id='" + modalGoogleMap + "' class='modal card-header div_modal'>\n" +
                "\n" +
                "                                        <!-- Модальное содержание -->\n" +
                "                                        <div class='modal-content'>\n" +
                "                                            <span id='" + spanGoogleMap + "' class='close'>&times;</span>\n" +
                "\n" +
                "                                            <footer>\n" +
                "                                                <div class='footerBox'>\n" +
                "\n" +
                "                                                    <div class='footerInfoBox'>\n" +
                "                                                        <div style='display: flex; flex-direction: row; flex-wrap: nowrap;'>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                                <h6 >У відпусці : </h6>\n" +
                "                                                                <p >" + vacation + "</p>\n" +
                "                                                            </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                                <h6 >Біженець : </h6>\n" +
                "                                                                <p >" + refuge + "</p>\n" +
                "                                                            </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                            <h6 >Може працювати : </h6>\n" +
                "                                                            <p >" + able_for_work + "</p>\n" +
                "                                                        </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;' >\n" +
                "                                                                <h6 >Працює : </h6>\n" +
                "                                                                <p >" + work_remote + "</p>\n" +
                "                                                                <p >" + work_by_place + "</p>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                        <div style='display: flex; flex-direction: row; padding-left: 10%; width: 90%;'>\n" +
                "                                                            <h6 >Регіон : &nbsp;</h6>\n" +
                "                                                            <p >\n" +
                "                                                                <span >" + person_list[i].state + "</span>\n" +
                "                                                                <span >" + war_zone + "</span>\n" +
                "                                                            </p>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "                                            </footer>\n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>" +

                "</th>" +
                "<td>" + person_list[i].firstname + "</td>" +
                "<td>" + person_list[i].middle_name + "</td>" +
                "<td>" + person_list[i].directorate + "</td>" +
                "<td>" + person_list[i].phone + "</td>" +
                "<td>" + person_list[i].date_add.split('T')[0] + "</td>" +
                "<th class='pl-md-5 th_geolocation' >" +

                "\n" +
                "                                    <!-- Триггер / Открыть Модальный -->\n" +
                "                                    <a id='" + btnGoogleMap + "' href='#" + modalGoogleMap + "' >" + person_list[i].text_location + "</a>\n" +
                "\n" +
                "                                    <!-- Модальном окно -->\n" +
                "                                    <div id='" + modalGoogleMap + "' class='modal card-header text-center div_modal'>\n" +
                "\n" +
                "                                        <!-- Модальное содержание -->\n" +
                "                                        <div class='modal-content map'>\n" +
                "                                            <span id='" + spanGoogleMap + "' class='close'>&times;</span>\n" +
                "\n" +
                "                                            <footer>\n" +
                "                                                <div class='footerBox'>\n" +
                "\n" +
                "                                                    <div class='footerInfoBox'>\n" +
                "                                                        <div class='googleMap'>\n" +
                "                                                            <!--<iframe th:src=\"@{'https://www.google.com/maps/embed/v1/place?key=AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s&q=' + ${person.getGeolocation()} + '&center=' + ${person.getGeolocation()} + '&zoom=10&maptype=roadmap&language=ru'}\" allowfullscreen=\"\" loading=\"lazy\"></iframe>-->\n" +
                "                                                            <iframe src=" + person_list[i].link_geolocation + " allowfullscreen='' loading='lazy'></iframe>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "                                            </footer>\n" +
                "\n" +
                "                                         </div>\n" +
                "                                     </div>\n" +

                "</th>" +
                "<td>" + person_list[i].last_modified.split('T')[0]+ "</td>" +
                "</tr>";

            let row = tbody.insertRow(count);
            row.innerHTML = trString;
            count++;
        }
    }
    if(start <= 0){
        buttonTopMinus.setAttribute("style", "pointer-events: none;");
        buttonBottomMinus.setAttribute("style", "pointer-events: none;");
    }


    // GOOGLE MAP MODAL WINDOW

    let allThOfGeolocation = document.querySelectorAll("[class='pl-md-5 th_geolocation']");

    for (let i = 0; i < allThOfGeolocation.length; i++) {


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

}
buttonBottomPlus.onclick = function () {
    start += 20;
    end += 20;

    if(end > person_list.length)
        end = person_list.length - 1;

    console.log(start + " / " + end);

    let tbody = document.getElementsByTagName("tbody")[1];

    let count = 0;

    buttonTopMinus.removeAttribute("style");
    buttonBottomMinus.removeAttribute("style");

    if(end < person_list.length ){

        tbody.innerHTML = "";

        for (let i = start ; i <= end ; i++) {

            let btnGoogleMap = "btnGoogleMap_" + i;
            let modalGoogleMap = "modalGoogleMap_" + i;
            let spanGoogleMap = "spanGoogleMap_" + i;
            let vacation = person_list[i].vacation ? 'так' : 'ні';
            console.log(vacation);
            let refuge = person_list[i].refugee ? 'так' : 'ні';
            console.log(refuge);
            let able_for_work = person_list[i].able_for_work ? 'так' : 'ні';
            console.log(able_for_work);
            let work_remote = person_list[i].work_remote ? 'віддалено' : '';
            console.log(work_remote);
            let work_by_place = person_list[i].work_by_place ? 'за місцем роботи' : '';
            console.log(work_by_place);
            let war_zone = person_list[i].war_zone ? '&nbsp;- на даний час це є зона бойових дій' : '&nbsp;- на даний час це не є зоною бойових дій';
            console.log(war_zone);

            let trString = "<tr class='pl-md-5' scope='row'><" +
                "th class='pl-md-5 th_geolocation' scope='row'>" +

                " <!-- Триггер / Открыть Модальный -->\n" +
                "                                    <a id='" + btnGoogleMap + "' href='#" + modalGoogleMap + "'>" + person_list[i].lastname + "</a>\n" +
                "\n" +
                "                                    <!-- Модальном окно -->\n" +
                "                                    <div id='" + modalGoogleMap + "' class='modal card-header div_modal'>\n" +
                "\n" +
                "                                        <!-- Модальное содержание -->\n" +
                "                                        <div class='modal-content'>\n" +
                "                                            <span id='" + spanGoogleMap + "' class='close'>&times;</span>\n" +
                "\n" +
                "                                            <footer>\n" +
                "                                                <div class='footerBox'>\n" +
                "\n" +
                "                                                    <div class='footerInfoBox'>\n" +
                "                                                        <div style='display: flex; flex-direction: row; flex-wrap: nowrap;'>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                                <h6 >У відпусці : </h6>\n" +
                "                                                                <p >" + vacation + "</p>\n" +
                "                                                            </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                                <h6 >Біженець : </h6>\n" +
                "                                                                <p >" + refuge + "</p>\n" +
                "                                                            </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;'  >\n" +
                "                                                            <h6 >Може працювати : </h6>\n" +
                "                                                            <p >" + able_for_work + "</p>\n" +
                "                                                        </div>\n" +
                "                                                            <div style='margin-right: 5%; width: 30%;' >\n" +
                "                                                                <h6 >Працює : </h6>\n" +
                "                                                                <p >" + work_remote + "</p>\n" +
                "                                                                <p >" + work_by_place + "</p>\n" +
                "                                                            </div>\n" +
                "                                                        </div>\n" +
                "                                                        <div style='display: flex; flex-direction: row; padding-left: 10%; width: 90%;'>\n" +
                "                                                            <h6 >Регіон : &nbsp;</h6>\n" +
                "                                                            <p >\n" +
                "                                                                <span >" + person_list[i].state + "</span>\n" +
                "                                                                <span >" + war_zone + "</span>\n" +
                "                                                            </p>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "                                            </footer>\n" +
                "\n" +
                "                                        </div>\n" +
                "                                    </div>" +

                "</th>" +
                "<td>" + person_list[i].firstname + "</td>" +
                "<td>" + person_list[i].middle_name + "</td>" +
                "<td>" + person_list[i].directorate + "</td>" +
                "<td>" + person_list[i].phone + "</td>" +
                "<td>" + person_list[i].date_add.split('T')[0] + "</td>" +
                "<th class='pl-md-5 th_geolocation' >" +

                "\n" +
                "                                    <!-- Триггер / Открыть Модальный -->\n" +
                "                                    <a id='" + btnGoogleMap + "' href='#" + modalGoogleMap + "' >" + person_list[i].text_location + "</a>\n" +
                "\n" +
                "                                    <!-- Модальном окно -->\n" +
                "                                    <div id='" + modalGoogleMap + "' class='modal card-header text-center div_modal'>\n" +
                "\n" +
                "                                        <!-- Модальное содержание -->\n" +
                "                                        <div class='modal-content map'>\n" +
                "                                            <span id='" + spanGoogleMap + "' class='close'>&times;</span>\n" +
                "\n" +
                "                                            <footer>\n" +
                "                                                <div class='footerBox'>\n" +
                "\n" +
                "                                                    <div class='footerInfoBox'>\n" +
                "                                                        <div class='googleMap'>\n" +
                "                                                            <!--<iframe th:src=\"@{'https://www.google.com/maps/embed/v1/place?key=AIzaSyCsFXx-hAhNheMt_16U1EABV8eqXmc38_s&q=' + ${person.getGeolocation()} + '&center=' + ${person.getGeolocation()} + '&zoom=10&maptype=roadmap&language=ru'}\" allowfullscreen=\"\" loading=\"lazy\"></iframe>-->\n" +
                "                                                            <iframe src=" + person_list[i].link_geolocation + " allowfullscreen='' loading='lazy'></iframe>\n" +
                "                                                        </div>\n" +
                "                                                    </div>\n" +
                "\n" +
                "                                                </div>\n" +
                "                                            </footer>\n" +
                "\n" +
                "                                         </div>\n" +
                "                                     </div>\n" +

                "</th>" +
                "<td>" + person_list[i].last_modified.split('T')[0] + "</td>" +
                "</tr>";

            let row = tbody.insertRow(count);
            row.innerHTML = trString;
            count++;
        }

    }

    end = start + 19;

    if (end >= person_list.length - 1){
        buttonTopPlus.setAttribute("style" , "pointer-events: none;");
        buttonBottomPlus.setAttribute("style" , "pointer-events: none;");
    }


    // GOOGLE MAP MODAL WINDOW


    let allThOfGeolocation = document.querySelectorAll("[class='pl-md-5 th_geolocation']");

    for (let i = 0; i < allThOfGeolocation.length; i++) {


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

}

