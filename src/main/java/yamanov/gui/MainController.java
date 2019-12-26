package yamanov.gui;

import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import yamanov.logic.AddFromFile;
import yamanov.logic.GetFileFromFolder;
import yamanov.logic.ToCSV;
import yamanov.logic.Value;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class MainController {

    private ShowAlert showAlert = new ShowAlert();
    private final static String PATH_TO_TESSDATA = new File(".").toString() + "/tessdata";
    private final static String CSV_NAME = "/order.csv";
    private AddFromFile addFromFile = new AddFromFile(PATH_TO_TESSDATA);

    @FXML
    private TextField pathToSCV;
    @FXML
    private TableView<Value> tableView;
    @FXML
    private TableColumn<Value, String> fileNameColumn;
    @FXML
    private TableColumn<Value, String> numberColumn;
    @FXML
    private TableColumn<Value, String> dateColumn;
    @FXML
    private TableColumn<Value, String> fioColumn;
    @FXML
    private TableColumn<Value, String> addressColumn;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button toSCVbutton;

    @FXML
    private void initialize() {
        fileNameColumn.setCellValueFactory(celldata -> celldata.getValue().fileNameProperty());
        fileNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numberColumn.setOnEditCommit((TableColumn.CellEditEvent<Value, String> t) ->
                (t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setNumber(t.getNewValue())
        );

        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        dateColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateColumn.setOnEditCommit((TableColumn.CellEditEvent<Value, String> t) ->
                (t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setDate(t.getNewValue())
        );

        fioColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        fioColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fioColumn.setOnEditCommit((TableColumn.CellEditEvent<Value, String> t) ->
                (t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setCustomer(t.getNewValue())
        );
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit((TableColumn.CellEditEvent<Value, String> t) ->
                (t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setAddress(t.getNewValue())
        );

        tableView.setPlaceholder(new Label("Нет данных для отчета"));
    }

    @FXML
    private void scanFolderAction(ActionEvent event) {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        File dir = directoryChooser.showDialog(new Stage());

        GetFileFromFolder folder = new GetFileFromFolder();
        if (dir != null) {
            pathToSCV.setText(dir.getAbsolutePath());
            ArrayList<String> filesList = folder.listFilesForFolder(dir);
            process(filesList);
        }
    }

    @FXML
    private void scanFileAction(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            pathToSCV.setText(file.getAbsolutePath().replaceFirst("\\\\[\\w-]+\\.[\\w]+$", ""));
            Value result = addFromFile.addFromFile(file.getAbsolutePath());
            addValueToTableInUI(result);
            toSCVbutton.setDisable(false);
        }
    }

    @FXML
    private void saveToSCV(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        ObservableList<Value> valuesList = tableView.getItems();
        if (valuesList.isEmpty()) {
            showAlert.showAlert("нет данных для создания отчета", Alert.AlertType.CONFIRMATION);
        } else {
            boolean result = ToCSV.addToCSV(pathToSCV.getText() + CSV_NAME, valuesList);
            if (result) {
                showAlert.showAlert("файл с данными создан", Alert.AlertType.CONFIRMATION);
                clearTable();
            } else {
                showAlert.showAlert("произошла ошибка при сохранении файла с результатом", Alert.AlertType.ERROR);
            }
        }
    }

    private void addValueToTableInUI(Value item) {
        ObservableList<Value> valuesList = tableView.getItems();
        if (item != null) {
            valuesList.add(item);
        }
    }

    private void clearTable() {
        tableView.getItems().clear();
        tableView.setPlaceholder(new Label("Нет данных для отчета"));
        toSCVbutton.setDisable(true);
    }

    private void process(List<String> files) {
        ProgressBarTask progressBarTask = new ProgressBarTask(files, addFromFile);
        tableView.setPlaceholder(new Label("Выполняется формирование списка значений"));
        tableView.getAccessibleText();
        progressBarTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                event -> {
                    List<Value> result = progressBarTask.getValue();
                    for (Value item : result) {
                        addValueToTableInUI(item);
                    }
                    toSCVbutton.setDisable(false);
                });

        progressBar.progressProperty().bind(progressBarTask.progressProperty());

        Thread parseFiles = new Thread(progressBarTask);
        parseFiles.setDaemon(true);
        parseFiles.start();

        progressBarTask.setOnFailed(e ->
                showAlert.showAlert("Ошибка в потоке обработки: " + e.toString(), Alert.AlertType.ERROR)
        );
    }
}
