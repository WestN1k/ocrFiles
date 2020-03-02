package yamanov.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import yamanov.database.entities.Inbox;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    private Stage rootStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.rootStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        stage.setTitle("OCR file app");
//        StackPane root = new StackPane();
        stage.setScene(new Scene(root, 800, 400));
        stage.show();
    }

    @Override
    public void init() throws Exception {
        System.out.println("Application inits");
        super.init();
    }

}
