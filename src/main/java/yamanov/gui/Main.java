package yamanov.gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
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
