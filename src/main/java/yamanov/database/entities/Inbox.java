package yamanov.database.entities;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "t_inbox")
public class Inbox {
    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "code")
    private int code;
    @Column(name = "prefix")
    private char prefix;
    @Column(name = "inboxnumber")
    private int inboxNumber;
    @Column(name = "inboxdate")
    private LocalDate inboxDate;
    @Column(name = "c_spr_doctype")
    private int cSprDocType;
    @Column(name = "docnumber")
    private StringProperty docNumber;
    @Column(name = "docdate")
    private StringProperty docDate;
    @Column(name = "c_spr_organization")
    private int cSprOrganization;
    @Column(name = "signedby")
    private String signedBy;
    @Column(name = "summary")
    private StringProperty summary;
    @Column(name = "c_spr_emloyee_executor")
    private int cSprEmplyeeExecutor;
    @Column(name = "executeupto")
    private String executeUpTo;
    @Column(name = "executedin")
    private String executedIn;
    @Column(name = "note")
    private String note;
    @Column(name = "object_count")
    private int objectCount;
    @Column(name = "replyto")
    private String replyTo;

    @Transient
    private StringProperty filename;
    @Transient
    private StringProperty customer;

    public Inbox(){
        this(null,null,null );
    }

    public Inbox(String docNumber, String docDate, String customer) {
        this.docNumber = new SimpleStringProperty(docNumber);
        this.docDate = new SimpleStringProperty(docDate);
        this.customer = new SimpleStringProperty(customer);
        this.filename = new SimpleStringProperty();
        this.inboxDate = LocalDate.now();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getFilename() {
        return filename.get();
    }

    public void setFilename(String filename) {
        this.filename.set(filename);
    }

    public char getPrefix() {
        return prefix;
    }

    public void setPrefix(char prefix) {
        this.prefix = prefix;
    }

    public int getInboxNumber() {
        return inboxNumber;
    }

    public void setInboxNumber(int inboxNumber) {
        this.inboxNumber = inboxNumber;
    }

    public String getInboxDate() {
        return this.inboxDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public void setInboxDate(LocalDate inboxDate) {
        this.inboxDate = inboxDate;
    }

    public int getcSprDocType() {
        return cSprDocType;
    }

    public void setcSprDocType(int cSprDocType) {
        this.cSprDocType = cSprDocType;
    }

    public String getDocNumber() {
        return docNumber.get();
    }

    public void setDocNumber(String docNumber) {
        this.docNumber.set(docNumber);
    }

    public String getDocDate() {
        return docDate.get();
    }

    public void setDocDate(String docDate) {
        this.docDate.set(docDate);
    }

    public int getcSprOrganization() {
        return cSprOrganization;
    }

    public void setcSprOrganization(int cSprOrganization) {
        this.cSprOrganization = cSprOrganization;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public String getSummary() {
        return summary.get();
    }

    public void setSummary(String summary) {
        this.summary.set(summary);
    }

    public int getcSprEmplyeeExecutor() {
        return cSprEmplyeeExecutor;
    }

    public void setcSprEmplyeeExecutor(int cSprEmplyeeExecutor) {
        this.cSprEmplyeeExecutor = cSprEmplyeeExecutor;
    }

    public String getExecuteUpTo() {
        return executeUpTo;
    }

    public void setExecuteUpTo(String executeUpTo) {
        this.executeUpTo = executeUpTo;
    }

    public String getExecutedIn() {
        return executedIn;
    }

    public void setExecutedIn(String executedIn) {
        this.executedIn = executedIn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getObjectCount() {
        return objectCount;
    }

    public void setObjectCount(int objectCount) {
        this.objectCount = objectCount;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getCustomer() {
        return customer.get();
    }

    public void setCustomer(String customer) {
        this.customer.set(customer);
    }

    public String getFormatedSummary() {
        return "сведения о" + getCustomer();
    }

    public String[] getListValues() {
        return new String[]{getFilename(), getDocNumber(), getDocDate(), getCustomer()};
    }

    public StringProperty numberProperty() {
        return docNumber;
    }

    public StringProperty dateProperty() {
        return docDate;
    }

    public StringProperty customerProperty() {
        return customer;
    }

    public StringProperty fileNameProperty() {
        return filename;
    }
}
