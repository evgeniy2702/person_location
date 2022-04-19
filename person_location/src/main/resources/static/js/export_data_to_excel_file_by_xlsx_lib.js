
// EXPORT DATA ABOUT PERSONS TO EXCEL FILE

document.getElementById("excelWrite").addEventListener('click', downLoadAsExcel);

// TITLE OF CREATING SHEET
const TITLE = [
    "№ з/п(id)", "Ім'я", "Прізвище", "По батькові", "№ телефону", "Координати", "Дата додавання до БД", "Посада",
    "Дата останнього оновлення данних", "Департамент", "Адреса знаходження", "Ссилка по геолокації", "Відпустка",
    "Біженець", "Область", "Знаходиться у зоні бойових дій", "Може працювати за місцем роботи", "Може працювати віддалено",
    "Може працювати", "Довго не оновлював дані", "Знаходиться на окупованій території"
];

const EXCEL_TYPE_XLSX = 'application/vnd.openxmlformats-officedocument.spreedsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION_XLSX = '.xlsx';

function downLoadAsExcel() {
    let workbook = XLSX.utils.book_new();
    workbook.Props = {
        Title:"Sheets Tutorial",
        Subject:"Test file",
        Author:"Zhurenko_Evgeniy",
        CreateDate: new Date(2022,4,18)
    };

    // WRITE NAME OF SHEET WHICH PLAN TO CREATE
    // workbook.SheetNames.push("Звіт_"+ date);

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

    // CAN NOT ADD STYLE TO CELL OF SHEET
    // worksheet['A1'].s = {
    //     font: {
    //         name: 'Arial',
    //         sz: 12,
    //         color: { rgb: "#FF000000" },
    //         bold: true,
    //         italic: false,
    //         underline: false
    //     }};

    worksheet['!cols'] = []; // we can add nest style : 'hidden':true/false; 'width'/'wpx'/'wpt':10(in MaxDigitWidth integral/
    // in pixel/ in point ); 'level':0-7; 'MDW': max digit width
    worksheet["!rows"] = []; // we can add next style ; 'hidden':true/false; 'hpx'/'hpt':10(in pixel/in point); 'level':0-7;

    worksheet['!cols'] = [
        {'width': 10}, //COLUMN A
        {'width': 12}, //COLUMN B
        {'width': 12}, //COLUMN C
        {'width': 15}, //COLUMN D
        {'width': 15}, //COLUMN E
        {'width': 20}, //COLUMN F
        {'width': 20}, //COLUMN G
        {'width': 15}, //COLUMN H
        {'width': 20}, //COLUMN I
        {'width': 12}, //COLUMN J
        {'width': 40}, //COLUMN K
        {'width': 30}, //COLUMN L
        {'width': 10}, //COLUMN M
        {'width': 10}, //COLUMN N
        {'width': 20}, //COLUMN O
        {'width': 10}, //COLUMN P
        {'width': 10}, //COLUMN Q
        {'width': 10}, //COLUMN R
        {'width': 10}, //COLUMN S
        {'width': 10}, //COLUMN T
        {'width': 10}  //COLUMN U
    ];

    worksheet['!rows'] = [
        {'hpx' : 30} //ROW 1
    ];

    // DATE TAKES FROM INDEX.HTML FROM SCRIPT date = [[${date}]]
    XLSX.utils.book_append_sheet(workbook, worksheet, "Звіт_"+ date);

    // WRITE SHEET WHICH WAS CREATE IN ARRAY OF SHEETS OF WORKBOOK BY NAME /"Звіт_" + date/
    // workbook.Sheets["Звіт_" + date] = worksheet;

    // Доступ к ячейке А1
    // console.dir(worksheet['A1']);
    // А1:U2985
    // console.dir(worksheet['!ref']);

    console.dir(workbook);

    let workbookOut = XLSX.write(workbook,{bookType:'xlsx',type:'binary',cellStyles: true});

    function stringToArrayBuffer(s){
        let buffer = new ArrayBuffer(s.length);
        let view = new Uint8Array(buffer);
        for (let i = 0; i<s.length; i++)
            view[i] = s.charCodeAt(i) & 0xFF;
        return buffer;
    }

    // SAVE DATA AS EXCEL FILE IN SHEET WITH NAME 'Звіт_' + date + EXCEL_EXTENSION_XLSX
    saveAs(new Blob([stringToArrayBuffer(workbookOut)],{type:EXCEL_TYPE_XLSX}), 'Звіт_' + date + EXCEL_EXTENSION_XLSX)
}

