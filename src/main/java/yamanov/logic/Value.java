package yamanov.logic;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Value {
    private final StringProperty filename;
    private final StringProperty number;
    private final StringProperty date;
    private final StringProperty customer;
    private final StringProperty address;

    public Value() {
        this(null, null, null, null, null);
    }

    public Value(String filename, String number, String date, String customer, String address) {
        this.filename = new SimpleStringProperty(filename);
        this.number = new SimpleStringProperty(number);
        this.date = new SimpleStringProperty(date);
        this.customer = new SimpleStringProperty(customer);
        this.address = new SimpleStringProperty(address);
    }

    public String getFilename() {
        return filename.get();
    }

    public void setFilename(String filename) {
        this.filename.set(filename);
    }

    public StringProperty fileNameProperty() { return filename; }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getCustomer() {
        return customer.get();
    }

    public StringProperty customerProperty() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer.set(customer);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String[] getListValues() {
        return new String[]{getFilename(), getNumber(), getDate(), getCustomer(), getAddress()};
    }
}
