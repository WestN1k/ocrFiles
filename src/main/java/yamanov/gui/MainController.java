package yamanov.gui;

import yamanov.logic.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;


public class MainController {

    private final static String PATH_TO_TESSDATA = new File (".").toString() + "/tessdata";
    private final static String CSV_NAME = "/order.csv";

    @FXML
    private TextField pathToSCV;
    @FXML
    private TableView<Value> tableView;
    @FXML
    private TableColumn<Value, String> numberColumn;
    @FXML
    private TableColumn<Value, String> dateColumn;
    @FXML
    private TableColumn<Value, String> fioColumn;
    @FXML
    private TableColumn<Value, String> addressColumn;

    @FXML
    private void initialize() {
        numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numberColumn.setOnEditCommit((TableColumn.CellEditEvent<Value, String> t) ->
                ( t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setNumber(t.getNewValue())
            );

        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit((TableColumn.CellEditEvent<Value, String> t) ->
                ( t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setDate(t.getNewValue())
            );

        fioColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        fioColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fioColumn.setOnEditCommit((TableColumn.CellEditEvent<Value, String> t) ->
                ( t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setCustomer(t.getNewValue())
            );
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit((TableColumn.CellEditEvent<Value, String> t) ->
                ( t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setAddress(t.getNewValue())
            );
    }

    @FXML
    private void scanFolderAction(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(new Stage());

        GetFileFromFolder folder = new GetFileFromFolder();
        if (dir != null) {
            pathToSCV.setText(dir.getAbsolutePath());
            ArrayList<String> filesList = folder.listFilesForFolder(dir);
            for (String path: filesList) {
                addToFile(path);
            }
        }
    }

    @FXML
    private void scanFileAction(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            pathToSCV.setText(file.getAbsolutePath().replaceFirst("\\\\[\\w-]+\\.[\\w]+$", ""));
            addToFile(file.getAbsolutePath());
        }
    }

    @FXML
    private void saveToSCV(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        ObservableList<Value> valuesList = tableView.getItems();

        if (valuesList.isEmpty()) {
            showAlert("нет данных для создания отчета", Alert.AlertType.CONFIRMATION);
        } else {
            boolean result = ToCSV.addToCSV(pathToSCV.getText() + CSV_NAME, valuesList);
            if (result) {
                showAlert("файл с данными создан", Alert.AlertType.CONFIRMATION);
                clearTable();
            } else {
                showAlert("произошла ошибка", Alert.AlertType.ERROR);
            }
        }
    }

    private void addToFile(String filePath) throws FileNotFoundException, UnsupportedEncodingException{
        FileOCR ocr = new FileOCR();
        ParseString parse = new ParseString();
        File pathToTessdata = new File(PATH_TO_TESSDATA);
        System.out.println(PATH_TO_TESSDATA);
        if ( pathToTessdata.exists() && pathToTessdata.isDirectory()) {
            if (!filePath.isBlank()) {
                if (filePath.endsWith(".jpg") || filePath.endsWith(".png")) {
                    String result = ocr.getStringFromFile(filePath, PATH_TO_TESSDATA);
                    Value value = parse.parseData(result);
                    addValue(value);
                }
            }
        } else {
            showAlert("не найдена папка tessdata", Alert.AlertType.ERROR);
        }
    }

    private void addValue(Value item) {
        ObservableList<Value> valuesList = tableView.getItems();
        valuesList.add(item);
    }

    private void clearTable() {
        tableView.getItems().clear();
    }

    private void showAlert(String textAlert, Alert.AlertType type) {
        Alert alert = new Alert(type, textAlert, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.CANCEL) {
            alert.close();
        }
    }

}
