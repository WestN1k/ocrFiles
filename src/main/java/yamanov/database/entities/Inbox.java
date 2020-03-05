package yamanov.database.entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "t_inbox")
public class Inbox {
    @Id
    @GeneratedValue(generator = "increment")
    @Column(name = "code")
    private Integer code;
    @Column(name = "inboxdate")
    private LocalDate inboxDate;
    @Column(name = "c_spr_doctype")
    private Integer cSprDocType;
    @Column(name = "docdate")
    private LocalDate docDate;
    @Column(name = "c_spr_organization")
    private Integer cSprOrganization;
    @Column(name = "signedby")
    private String signedBy;
    @Column(name = "c_spr_employee_executor")
    private Integer cSprEmployeeExecutor;
    @Column(name = "executeupto")
    private LocalDate executeUpTo;
    @Column(name = "executedin")
    private LocalDate executedIn;
    @Column(name = "note")
    private String note;
    @Column(name = "object_count")
    private Integer objectCount;
    @Column(name = "replyto")
    private String replyTo;
    @Column(name = "docnumber")
    private String docNumber;

    @Transient
    @Column(name = "inboxnumber")
    private Integer inboxNumber;
    @Transient
    @Column(name = "prefix")
    private char prefix;

    @Transient
    private StringProperty docNumberProperty;

    @Transient
    private StringProperty filenameProperty;

    @Transient
    private StringProperty customerProperty;

    public Inbox(){
        this(null,null,null );
    }

    public Inbox(String docNumber, String docDate, String customer) {
        this.docNumberProperty = new SimpleStringProperty(docNumber);
        this.customerProperty = new SimpleStringProperty(customer);
        this.filenameProperty = new SimpleStringProperty();
        this.inboxDate = LocalDate.now();
    }

    public int getCode() {
        return code;
    }

    public String filename() {
        return filenameProperty.get();
    }

    public void filenameSet(String filename) {
        this.filenameProperty.set(filename);
    }

    public char getPrefix() {
        return prefix;
    }

    public void setPrefix(char prefix) {
        this.prefix = prefix;
    }

    public Integer getInboxNumber() {
        return inboxNumber;
    }

    public void setInboxNumber(Integer inboxNumber) {
        this.inboxNumber = inboxNumber;
    }

    public LocalDate getInboxDate() {
        return this.inboxDate;
    }

    public void setInboxDate(LocalDate inboxDate) {
        this.inboxDate = inboxDate;
    }

    public Integer getcSprDocType() {
        return cSprDocType;
    }

    public void setcSprDocType(Integer cSprDocType) {
        this.cSprDocType = cSprDocType;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber= docNumber;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }

    public Integer getcSprOrganization() {
        return cSprOrganization;
    }

    public void setcSprOrganization(Integer cSprOrganization) {
        this.cSprOrganization = cSprOrganization;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public void setSignedBy(String signedBy) {
        this.signedBy = signedBy;
    }

    public String getSummary() {
        return "сведения о" + customer();
    }

    public void setSummary(String summary) {
        customerSet(summary);
    }

    public Integer getcSprEmployeeExecutor() {
        return cSprEmployeeExecutor;
    }

    public void setcSprEmployeeExecutor(int cSprEmplyeeExecutor) {
        this.cSprEmployeeExecutor = cSprEmplyeeExecutor;
    }

    public LocalDate getExecuteUpTo() {
        return executeUpTo;
    }

    public void setExecuteUpTo(LocalDate executeUpTo) {
        this.executeUpTo = executeUpTo;
    }

    public LocalDate getExecutedIn() {
        return executedIn;
    }

    public void setExecutedIn(LocalDate executedIn) {
        this.executedIn = executedIn;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getObjectCount() {
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

    public String customer() {
        return customerProperty.get();
    }

    public void customerSet(String customer) {
        this.customerProperty.set(customer);
        this.replyTo = "сведения о " + customer;
    }

    public String[] listValues() {
        return new String[]{filename(), getDocNumber(), getDocDate().toString(), customer()};
    }

    public StringProperty numberProperty() {
        return new SimpleStringProperty(getDocNumber());
    }

    public StringProperty customerProperty() {
        return customerProperty;
    }

    public LocalDate localDocDate() {
        return docDate;
    }
    public StringProperty fileNameProperty() {
        return filenameProperty;
    }
}
