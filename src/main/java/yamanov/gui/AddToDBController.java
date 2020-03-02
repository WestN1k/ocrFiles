package yamanov.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import yamanov.database.SprOrganizationService;
import yamanov.database.entities.Inbox;
import yamanov.database.entities.SprOrganization;

import java.util.ArrayList;
import java.util.List;

public class AddToDBController {
    private Stage addToDBStage;
    private List<Inbox> inboxList;
    private List<String> orgs;
    private List<SprOrganization> organizations;

    @FXML
    private DatePicker dateInbox;
    @FXML
    private DatePicker dateReplyTo;
    @FXML
    private TextField organization;
    @FXML
    private TextField author;

    @FXML
    private void initialize() {

        organization.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                orgs = new ArrayList<>();
                if (organization.getText().length() > 3) {
                    SprOrganizationService orgService = new SprOrganizationService();
                    organizations = orgService.getOrganizationsByLike(organization.getText());
                    for(SprOrganization org: organizations) {
                        orgs.add(org.getName());
                    }
                    TextFields.bindAutoCompletion(organization, orgs);
                }
            }
        });
    }

    public void setDialogStage(Stage addToDBStage) {
        this.addToDBStage = addToDBStage;
    }

    public void setInboxList(List<Inbox> inboxList) {
        this.inboxList = inboxList;
    }

    @FXML
    public void cancel(Event event) {
        System.out.println("как обработать закрытие по кнопке");
    }

    @FXML
    public void addToDataBase(Event event) {

    }

}
