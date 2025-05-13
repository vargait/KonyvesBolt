module hu.adatba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.dotenv;

    opens hu.adatba to javafx.fxml;
    exports hu.adatba;
    exports hu.adatba.Model;
    exports hu.adatba.Controller;
    exports hu.adatba.Service;
    exports hu.adatba.DAO;
    opens hu.adatba.Controller to javafx.fxml;
}