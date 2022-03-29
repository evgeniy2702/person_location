// INPUT PHONE NUMBER ACCORDING EXAMPLE 067-222-22-22

let inputPhone = document.getElementById('tel');

inputPhone.addEventListener("keydown", (event) => {
    var text = event.target.value;
    console.log(event.code + " " + event.key);
    if (event.key !== "Enter") {
        if (text.length === 3 || text.length === 7 || text.length === 10) {
            if (event.key === "Delete" || event.key === "Backspace") {
                text = event.target.value.delete("-");
            } else {
                text = event.target.value.concat("-");
            }

            inputPhone.value = text;
            console.log(text + " " + inputPhone);
        }
        if (text.length >= 13) {
            event.target.value.concat('');
        }
    }

});