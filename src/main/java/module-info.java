module OCRfiles {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires tess4j;
    requires opencsv;

    opens yamanov.gui to javafx.fxml;
    exports yamanov.gui;
    exports yamanov.logic;
}