package yamanov.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseString {

    private Value item = new Value();

    public Value parseData(String data) {
        String headNumberPatternRegex = "[0-9]{10}";
        String headDatePatternRegex = "от\\s\\d{2}\\.\\d{2}\\.\\d{4}\\n";
        String fioPatternRegex = ":\\s[А-Яа-я]+\\s[А-Яа-я]+\\s[А-Яа-я]{2,},";
        String addressPatternRegex = "адрес:.*\\s*.+\\.\\n";

        Pattern numberPattern = Pattern.compile(headNumberPatternRegex);
        Pattern datePattern = Pattern.compile(headDatePatternRegex);
        Pattern fioPattern = Pattern.compile(fioPatternRegex);
        Pattern addressPattern = Pattern.compile(addressPatternRegex);

        Matcher number = numberPattern.matcher(data);
        Matcher date = datePattern.matcher(data);
        Matcher fio = fioPattern.matcher(data);
        Matcher address = addressPattern.matcher(data);

        if (number.find()) {
            item.setNumber(number.group(0).strip());
        } else {
            System.out.println("number not found");
        }

        if (date.find()) {
            item.setDate(date.group(0).replaceAll("от", "").strip());
        } else {
            System.out.println("date not found");
        }

        if (fio.find()) {
            item.setCustomer(fio.group(0).replaceAll("[:,.\\n\\r]+", "").strip());
        } else {
            System.out.println("fio not found");
        }

        if (address.find()) {
            item.setAddress(address.group(0).replaceAll("адрес[:\\n\\r]+", "").strip());
        } else {
            System.out.println("address not found");
        }

        return item;
    }

}
