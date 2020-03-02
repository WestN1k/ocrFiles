package yamanov.logic;

import yamanov.database.entities.Inbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseString {

    private Inbox item = new Inbox();

    public Inbox parseData(String data, String fileName) {
        String headNumberPatternRegex = "[0-9]{10}";
        String headDatePatternRegex = "от\\s\\d{2}\\.\\d{2}\\.\\d{4}\\n";
        String fioPatternRegex = "должника[:;].[а-яА-Я\\s]+,";  //":\\s[А-Яа-я]+\\s[А-Яа-я]+\\s[А-Яа-я]{2,},";
//        String addressPatternRegex = "адрес:.*\\s*.+\\.\\n";

        Pattern numberPattern = Pattern.compile(headNumberPatternRegex);
        Pattern datePattern = Pattern.compile(headDatePatternRegex);
        Pattern fioPattern = Pattern.compile(fioPatternRegex);
//        Pattern addressPattern = Pattern.compile(addressPatternRegex);

        Matcher number = numberPattern.matcher(data);
        Matcher date = datePattern.matcher(data);
        Matcher fio = fioPattern.matcher(data);
//        Matcher address = addressPattern.matcher(data);

        if (fileName != null && !fileName.isEmpty()){
            item.setFilename(fileName.trim());
        } else {
            System.out.println("filename not found");
        }

        if (number.find()) {
            item.setDocNumber(number.group(0).trim().strip());
        } else {
            System.out.println("number not found");
        }

        if (date.find()) {
            item.setDocDate(date.group(0).replaceAll("от", "").trim().strip());
        } else {
            System.out.println("date not found");
        }

        if (fio.find()) {
            item.setCustomer(fio.group(0).replaceAll("должника[;:\n\r]+", "")
                    .replace(",", "")
                    .replace("\n", "").strip());
        } else {
            System.out.println("fio not found");
        }

//        if (address.find()) {
//            item.setAddress(address.group(0).replaceAll("адрес[:;]+", "")
//                    .replace(",", "")
//                    .replace("\n", "")
//                    .replace(" -", "")
//                    .strip());
//        } else {
//            System.out.println("address not found");
//        }

        return item;
    }

}
