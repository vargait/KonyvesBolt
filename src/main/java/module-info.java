module hu.adatba {
    requires javafx.controls;
    requires javafx.fxml;

    opens hu.adatba to javafx.fxml;
    exports hu.adatba;
}