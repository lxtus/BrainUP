module com.junks.brainup {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.junks.brainup.domain to com.google.gson;
    opens com.junks.brainup.controller to javafx.fxml;
    exports com.junks.brainup;
}