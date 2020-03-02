package yamanov.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yamanov.database.entities.Inbox;

import java.io.IOException;
import java.util.List;

public class AddToDBApp {

    public boolean showAddToDBDialog(List<Inbox> inboxList) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addToDB.fxml"));
            AnchorPane page = loader.load();
            Stage addToDBStage = new Stage();
            addToDBStage.setTitle("добавление в базу данных");
            addToDBStage.initModality(Modality.WINDOW_MODAL);
            addToDBStage.setScene(new Scene(page));

            AddToDBController addToDBController = loader.getController();
            addToDBController.setDialogStage(addToDBStage);
            addToDBController.setInboxList(inboxList);
            addToDBStage.showAndWait();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
