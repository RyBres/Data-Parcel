module rybres.dataparcel {
    requires javafx.controls;
    requires javafx.fxml;

    opens rybres.dataparcel to javafx.fxml;
    exports rybres.dataparcel;
    requires univocity.parsers;
    requires java.base;
}
