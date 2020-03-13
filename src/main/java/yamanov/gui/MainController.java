package yamanov.gui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.bytebuddy.asm.Advice;
import yamanov.database.entities.Inbox;
import yamanov.logic.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class MainController {

    private ShowAlert showAlert = new ShowAlert();
    private final static String CSV_NAME = "/order.csv";
    private AddFromFile addFromFile = new AddFromFile(getPathToFolder("tessdata"));

    @FXML
    private TextField pathToSCV;
    @FXML
    private TableView<Inbox> tableView;
    @FXML
    private TableColumn<Inbox, String> fileNameColumn;
    @FXML
    private TableColumn<Inbox, String> numberColumn;
    @FXML
    private TableColumn<Inbox, LocalDate> dateColumn;
    @FXML
    private TableColumn<Inbox, String> fioColumn;
//    @FXML
//    private TableColumn<Inbox, String> addressColumn;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button toSCVbutton;
    @FXML
    private Button toDataBaseButton;


    @FXML
    private void initialize() {
        fileNameColumn.setCellValueFactory(celldata -> celldata.getValue().fileNameProperty());
        fileNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        numberColumn.setCellValueFactory(cellData -> cellData.getValue().numberProperty());
        numberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numberColumn.setOnEditCommit((TableColumn.CellEditEvent<Inbox, String> t) ->
                (t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).setDocNumber(t.getNewValue())
        );

        dateColumn.setCellValueFactory(param -> new SimpleObjectProperty<LocalDate>(new DatePicker(param.getValue().localDocDate()).getValue()));
        dateColumn.setCellFactory(LocalDateTableCell :: new);
        dateColumn.setOnEditCommit(event -> event.getTableView().getSelectionModel().getSelectedItem().setDocDate(event.getNewValue()));

        fioColumn.setCellValueFactory(cellData -> cellData.getValue().customerProperty());
        fioColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fioColumn.setOnEditCommit((TableColumn.CellEditEvent<Inbox, String> t) ->
                (t.getTableView().getItems().get(t.getTablePosition().getRow())
                ).customerSet(t.getNewValue())
        );
//        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
//        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        addressColumn.setOnEditCommit((TableColumn.CellEditEvent<Inbox, String> t) ->
//                (t.getTableView().getItems().get(t.getTablePosition().getRow())
//                ).setAddress(t.getNewValue())
//        );

        tableView.setPlaceholder(new Label("Нет данных для отчета"));
    }

    @FXML
    private void scanFolderAction(ActionEvent event) {

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(Objects.requireNonNull(getPathToFolder(""))));
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
        fileChooser.setInitialDirectory(new File(Objects.requireNonNull(getPathToFolder(""))));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            pathToSCV.setText(file.getAbsolutePath().replaceFirst("\\\\[\\w-]+\\.[\\w]+$", ""));
            Inbox result = addFromFile.addFromFile(file.getAbsolutePath());
            addValueToTableInUI(result);
            toSCVbutton.setDisable(false);
        }
    }

    @FXML
    private void saveToDatabase(ActionEvent event) throws UnsupportedEncodingException {
        ObservableList<Inbox> valuesList = tableView.getItems();
        // добавить проверку на заполнение таблицы
        if (valuesList.isEmpty()) {
            showAlert.showAlert("нет данных для переноса в базу", Alert.AlertType.CONFIRMATION);
        } else {
            AddToDBApp app = new AddToDBApp();
            boolean res = app.showAddToDBDialog(valuesList);
            if (res) {
                tableView.getItems().clear();
                tableView.setPlaceholder(new Label("Нет данных для отчета"));
            }
        }
    }

    @FXML
    private void saveToSCV(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        ObservableList<Inbox> valuesList = tableView.getItems();
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

    private static String getPathToFolder(String folderName){
        try {
            File mainClassFile = new File(Launcher.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            return  mainClassFile.getParent() + File.separator + folderName;
        } catch (URISyntaxException e){
            e.printStackTrace();
            return null;
        }
    }

    private void addValueToTableInUI(Inbox item) {
        ObservableList<Inbox> valuesList = tableView.getItems();
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
                    List<Inbox> result = progressBarTask.getValue();
                    for (Inbox item : result) {
                        addValueToTableInUI(item);
                    }
                    toSCVbutton.setDisable(false);
                });

        progressBar.progressProperty().bind(progressBarTask.progressProperty());

        Thread parseFiles = new Thread(progressBarTask);
        parseFiles.setDaemon(true);
        parseFiles.start();

        //переработать, т.к. реагирует и на отсутствие папки tessdata, обработчик tess4j, в этом случае не срабатывает
        progressBarTask.setOnFailed(e ->
                showAlert.showAlert("Ошибка в потоке обработки: " + e.getSource().getException().toString(), Alert.AlertType.ERROR)
        );
    }

    public String encodeToUTF8(String stringToEncode) {
        byte[] stringBytes = stringToEncode.getBytes(StandardCharsets.ISO_8859_1);
        return new String(stringBytes, StandardCharsets.UTF_8);
    }
}
