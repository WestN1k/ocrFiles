package yamanov.logic;

import javafx.scene.control.Alert;
import yamanov.gui.ShowAlert;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddFromFile {

    private String pathToTess;

    public AddFromFile(String pathToTess) {
        this.pathToTess = pathToTess;
    }

    public Value addFromFile(String filePath) {
        FileOCR ocr = new FileOCR();
        ParseString parse = new ParseString();
        ShowAlert showAlert = new ShowAlert();
        File pathToTessdata = new File(pathToTess);
        Path path = Paths.get(filePath);
        Path filename = path.getFileName();

        if (pathToTessdata.exists() && pathToTessdata.isDirectory()) {
            if (!filePath.isBlank()) {
                if (filePath.endsWith(".jpg") || filePath.endsWith(".png")) {
                    try {
                        String result = ocr.getStringFromFile(filePath, pathToTess);
                        return parse.parseData(result, filename.toString());
                    } catch (Exception e) {
                        showAlert.showAlert(e.getMessage(), Alert.AlertType.ERROR);
                    }
                }
            }
        } else {
            showAlert.showAlert("не найдена папка tessdata", Alert.AlertType.ERROR);
        }
        return null;
    }
}
