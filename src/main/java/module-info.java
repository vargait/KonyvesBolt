module hu.adatba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens hu.adatba to javafx.fxml;
    exports hu.adatba;
}