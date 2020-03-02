module OCRfiles {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires tess4j;
    requires opencsv;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires org.controlsfx.controls;
    requires java.sql;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires java.xml;
    requires com.sun.xml.bind;
    requires com.fasterxml.classmate;

    exports yamanov.gui;
    exports yamanov.logic;
    opens yamanov.database.entities to org.hibernate.orm.core;
    opens yamanov.gui to javafx.fxml;
    opens yamanov.database;
}