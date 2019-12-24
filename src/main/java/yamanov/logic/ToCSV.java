package yamanov.logic;

import com.opencsv.CSVWriter;

import java.io.*;
import java.util.List;

public class ToCSV extends IOException {

    public static boolean addToCSV(String csvPath, List<Value> values) throws FileNotFoundException, UnsupportedEncodingException {
        OutputStreamWriter file = new OutputStreamWriter(new FileOutputStream(csvPath, true), "Windows-1251");
        try {
            CSVWriter writer = new CSVWriter(file, ';',
                                            CSVWriter.DEFAULT_QUOTE_CHARACTER,
                                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                            CSVWriter.DEFAULT_LINE_END
                                            );
            for (Value item: values) {
                writer.writeNext(item.getListValues());
            }
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
