package yamanov.gui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import yamanov.database.InboxService;
import yamanov.database.SprOrganizationService;
import yamanov.database.entities.Inbox;
import yamanov.database.entities.SprOrganization;

import javax.crypto.spec.PSource;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntBinaryOperator;

public class AddToDBController {
    private Stage addToDBStage;
    private List<Inbox> inboxList;
    private List<String> orgs;
    private List<SprOrganization> organizations;
    private boolean isOk;

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
                if (organization.getText().length() > 3) {
                    orgs = new ArrayList<>();
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
        addToDBStage.close();
    }

    public boolean handleOk() {
        return isOk;
    }

    @FXML
    public void addToDataBase(Event event) {
        Integer orgId = getSprOrganizationId();
        for (Inbox inbox: inboxList) {
            inbox.setcSprOrganization(orgId);
            inbox.setSignedBy(author.getText());
            inbox.setExecutedIn(dateInbox.getValue());
            inbox.setExecuteUpTo(dateReplyTo.getValue());
        }
        InboxService inboxService = new InboxService();
        boolean result = inboxService.bulkInsert(inboxList);
        if (result) {
            new ShowAlert().showAlert("данные записаны", Alert.AlertType.INFORMATION);
            isOk = true;
        } else {
            new ShowAlert().showAlert("заполнены не все поля", Alert.AlertType.WARNING);
            isOk = false;
        }
        addToDBStage.close();
    }

    private Integer getSprOrganizationId() {
        try{
            for (SprOrganization org : organizations) {
                if (org.getName().equals(organization.getText())) {
                    return org.getCode();
                }
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    public String encodeToUTF8(String stringToEncode) throws UnsupportedEncodingException {
        byte[] stringBytes = stringToEncode.getBytes(StandardCharsets.ISO_8859_1);
        return new String(stringBytes, StandardCharsets.UTF_8);
    }

    public String encode(String stringToEncode) throws UnsupportedEncodingException {
        return new String(stringToEncode.getBytes("windows-1251"), StandardCharsets.UTF_8);
    }
}
