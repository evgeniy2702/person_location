

// EXPORT DATA ABOUT PERSONS TO EXCEL FILE

document.getElementById("excelWrite").addEventListener('click', downLoadAsExcel);

// TITLE OF CREATING SHEET
const TITLE = [
    "№ з/п(id)", "Ім'я", "Прізвище", "По батькові", "№ телевону", "Координати", "Дата додавання до БД", "Посада",
    "Дата останнього оновлення данних", "Департамент", "Адреса знаходження", "Ссилка по геолокації", "Відпустка",
    "Біженець", "Область", "Знаходиться у зоні бойових дій", "Може працювати за місцем роботи", "Може працювати віддалено",
    "Може працювати", "Довго не обновлювал данні", "Знаходиться на окупованій території"
];

const EXCEL_TYPE_XLSX = 'application/vnd.openxmlformats-officedocument.spreedsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION_XLSX = '.xlsx';

function downLoadAsExcel() {
    let workbook = XLSX.utils.book_new();
    workbook.Props = {
        Title:"Sheets Tutorial",
        Subject:"Test file",
        Author:"Zhurenko_Evgeniy",
        CreateDate: new Date().getUTCDate()
    };
    workbook.SheetNames.push("Звіт_"+ date);

    let values = [];

    values.push(TITLE);

    // PERSON_LIST TAKES FROM INDEX.HTML FROM SCRIPT [[${session.#lists.toList(persons)}]]
    for(let value of person_list){
        for(const key of Object.keys(JSON.parse(JSON.stringify(value)))){
            if(value[key] === true)
                value[key] = 'так';
            if(value[key] === false)
                value[key] = 'ні';
            if(key === 'user_messenger_id')
                delete value[key];
        }
        values.push(Object.values(JSON.parse(JSON.stringify(value))));
    }

    let worksheet = XLSX.utils.aoa_to_sheet(values);

    // DATE TEKES FROM INDEX.HTML FROM SCRIPT date = [[${date}]]
    workbook.Sheets["Звіт_" + date] = worksheet;

    // Доступ к ячейке А1
    // console.dir(worksheet['A1']);
    // А1:U2985
    // console.dir(worksheet['!ref']);

    let workbookOut = XLSX.write(workbook,{bookType:'xlsx',type:'binary'});

    function stringToArrayBuffer(s){
        let buffer = new ArrayBuffer(s.length);
        let view = new Uint8Array(buffer);
        for (let i = 0; i<s.length; i++)
            view[i] = s.charCodeAt(i) & 0xFF;
        return buffer;
    }

    saveAs(new Blob([stringToArrayBuffer(workbookOut)],{type:EXCEL_TYPE_XLSX}), 'Звіт_' + date + EXCEL_EXTENSION_XLSX)
}

