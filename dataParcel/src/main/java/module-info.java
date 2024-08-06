module com.mycompany.dataparcel {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.dataparcel to javafx.fxml;
    exports com.mycompany.dataparcel;
}
