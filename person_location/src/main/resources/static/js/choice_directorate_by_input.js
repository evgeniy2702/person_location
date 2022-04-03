
// INPUT DIRECTORATE NAME FROM LIST OF DIRECTORATES OF EMPLOYEE

let inputDirectorate = document.getElementById("dir");

inputDirectorate.onclick = dep_cleanInput();

inputDirectorate.addEventListener("keydown", (event) => {
    if (!event.shiftKey && !event.altKey && !event.ctrlKey) {

        let directorate = event.target.value;
        let directorates = directorates_list;
        let createDirectoateList = [];
        let divDirectorates = document.getElementById("directorates");
        let div = document.createElement("div");

        for (let i = 0; i < directorates.length; i++) {
            console.log(directorate);

            if(directorates[i].length >= 2) {
                if (directorates[i].substring(0, (directorate.length)).toLowerCase() === directorate.toLowerCase() ) {

                    createDirectoateList.push(directorates[i]);
                }
            } else {
                if (directorates[i].substring(0, (directorate.length)).toLowerCase() === directorate.toLowerCase()){

                    createDirectoateList.push(directorates[i]);

                }
            }
        }

        let stringHTML = "";
        let count = 0;
        for (let elem of createDirectoateList) {
            count += 1;
            if(count <= 3) {
                stringHTML += "<p style='border: 1px solid black; border-top-color: black; " +
                    "border-right-color: black; border-bottom-color: black; border-left-color: black; " +
                    "border-color: gainsboro; width: 100%; margin-bottom: 0' >" + elem + "</p>";
            }
        }
        dep_detachWhenLengthPNull();
        if (stringHTML !== "") {

            div.innerHTML = stringHTML;
            div.setAttribute("display", "initial");
            div.setAttribute("text-align", "center");
            divDirectorates.appendChild(div);
            stringHTML = "";
        }
        if (event.target.value.length === 0) {
            dep_detachWhenLengthPNull();
        }
        if(event.target.value.length === 1 && event.key === 'Backspace'){
            dep_detachWhenLengthPNull();
        }
    }
    dep_cleanInput();
    dep_boldWordInInput();
    dep_deleteBoldWordInInput();
    dep_inputWordFromMenu();
});

//Ф-ция удаления содержимого поля input при клике
function dep_cleanInput() {
    let input = document.getElementById("dir");
    input.onclick = function () {
        input.value = '';
        dep_detachWhenLengthPNull();
    };
}

//Ф-ция выделения содержимого тега Р, на котором наведен курсор мышки
function dep_boldWordInInput() {
    let p = document.querySelectorAll('#directorates > div > p');
    p.forEach(p => p.onmouseover = function () {
        this.style.fontWeight = 'bold';
    });
}

//Ф-ция снятия выделения содержимого тега Р, с котором ушел курсор мышки
function dep_deleteBoldWordInInput() {
    let p = document.querySelectorAll('#directorates > div > p');
    p.forEach(p => p.onmouseleave = function () {
        this.style.fontWeight = '';
    })
}

//Ф-ция ввода в поле поиска региона из раскрываюещгося меню ниже этого поля
function dep_inputWordFromMenu(){
    let p = document.querySelectorAll('#directorates > div > p');
    let input = document.querySelector('#directorates > input[type=search]');
    p.forEach(p=> p.onclick = function(){
        input.value = p.innerText;
        console.log(input.value);
        dep_detachWhenLengthPNull();
    });
}

//Ф-ция удаления тегов Р из под input
function dep_detachWhenLengthPNull() {
    document.querySelectorAll('#directorates > div > p').forEach(p => p.remove(() => {
        this.detach();
    }));
}