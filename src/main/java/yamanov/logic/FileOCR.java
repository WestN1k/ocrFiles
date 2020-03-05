package yamanov.logic;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;


public class FileOCR {

    public String getStringFromFile(String filePath, String pathToTessdata) {
        System.out.println(filePath);
        File imageFile = new File(filePath);
        ITesseract tess = new Tesseract();
        tess.setLanguage("rus");
        tess.setDatapath(pathToTessdata);

        try {
            return tess.doOCR(imageFile);

        } catch (TesseractException e) {
            System.err.println(e.getMessage());
            return "not found";
        }
    }
}
