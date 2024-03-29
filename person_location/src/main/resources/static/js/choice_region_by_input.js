
// INPUT REGION NAME FROM LIST OF REGIONS OF EMPLOYEE

let inputRegion = document.getElementById("region");

inputRegion.onclick = reg_cleanInput();

inputRegion.addEventListener("keydown", (event) => {
    if (!event.shiftKey && !event.altKey && !event.ctrlKey) {

        let region = event.target.value;
        let regions = stateList;
        let createStateList = [];
        let divRegions = document.getElementById("regions");
        let div = document.createElement("div");

        for (let i = 0; i < regions.length; i++) {
            console.log(region);

            let arrayString = regions[i].split(" ");

            if(arrayString.length >= 2) {
                if (regions[i].substring(0, (region.length)).toLowerCase() === region.toLowerCase() ||
                    regions[i].split(" ")[1].substring(0, region.length).toLowerCase() === region.toLowerCase()) {

                    createStateList.push(regions[i]);
                }
            } else {
                if (regions[i].substring(0, (region.length)).toLowerCase() === region.toLowerCase()){

                    createStateList.push(regions[i]);

                }
            }
        }

        let stringHTML = "";
        let count = 0;
        for (let elem of createStateList) {
            count += 1;
            if(count <= 3) {
                stringHTML += "<p style='border: 1px solid black; border-top-color: black; " +
                    "border-right-color: black; border-bottom-color: black; border-left-color: black; " +
                    "border-color: gainsboro; width: 100%; margin-bottom: 0' >" + elem + "</p>";
            }
        }
        reg_detachWhenLengthPNull();
        if (stringHTML !== "") {

            div.innerHTML = stringHTML;
            div.setAttribute("display", "initial");
            div.setAttribute("text-align", "center");
            divRegions.appendChild(div);
            stringHTML = "";
        }
        if (event.target.value.length === 0) {
            reg_detachWhenLengthPNull();
        }
        if(event.target.value.length === 1 && event.key === 'Backspace'){
            reg_detachWhenLengthPNull();
        }
    }
    reg_cleanInput();
    reg_boldWordInInput();
    reg_deleteBoldWordInInput();
    reg_inputWordFromMenu();
});

//Ф-ция удаления содержимого поля input при клике
function reg_cleanInput() {
    let input = document.getElementById("region");
    input.onclick = function () {
        input.value = '';
        reg_detachWhenLengthPNull();
    };
}

//Ф-ция выделения содержимого тега Р, на котором наведен курсор мышки
function reg_boldWordInInput() {
    let p = document.querySelectorAll('#regions > div > p');
    p.forEach(p => p.onmouseover = function () {
        this.style.fontWeight = 'bold';
    });
}

//Ф-ция снятия выделения содержимого тега Р, с котором ушел курсор мышки
function reg_deleteBoldWordInInput() {
    let p = document.querySelectorAll('#regions > div > p');
    p.forEach(p => p.onmouseleave = function () {
        this.style.fontWeight = '';
    })
}

//Ф-ция ввода в поле поиска региона из раскрываюещгося меню ниже этого поля
function reg_inputWordFromMenu(){
    let p = document.querySelectorAll('#regions > div > p');
    let input = document.querySelector('#regions > input[type=search]');
    p.forEach(p=> p.onclick = function(){
        input.value = p.innerText;
        console.log(input.value);
        reg_detachWhenLengthPNull();
    });
}

//Ф-ция удаления тегов Р из под input
function reg_detachWhenLengthPNull() {
    document.querySelectorAll('#regions > div > p').forEach(p => p.remove(() => {
        this.detach();
    }));
}