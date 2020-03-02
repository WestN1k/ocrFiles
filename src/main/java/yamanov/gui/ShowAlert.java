package yamanov.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ShowAlert {

    public void showAlert(String textAlert, Alert.AlertType type) {
        Alert alert = new Alert(type, textAlert, ButtonType.CANCEL);
        alert.setTitle("информация");
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.CANCEL) {
            alert.close();
        }
    }

}
