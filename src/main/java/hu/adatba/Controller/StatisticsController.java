package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.DAO.StatisticsDAO;
import hu.adatba.Model.QueryResult;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class StatisticsController {
    // Adattagok
    @FXML
    private ComboBox<String> queryCB;

    @FXML
    private TableView<QueryResult> queryTV;

    @FXML
    private TableColumn<QueryResult, String> firstTC, secondTC;

    @FXML
    private Button cancelBTN;

    private final StatisticsDAO statisticsDAO = new StatisticsDAO();

    public StatisticsController() {
    }

    // Metódusok
    @FXML
    public void initialize() {
        queryCB.getItems().addAll(
                "Műfajonként hány könyv van",
                "Felhasználók rendelési száma",
                "TOP 5 legtöbbet rendelt könyv",
                "Törzsvásárlók (legalább 3 rendelés)",
                "Rendelések összértéke havonta",
                "Átlagos könyvár műfajonként",
                "Legtöbbet választott könyv",
                "Csak egy rendeléses felhasználók",
                "Felhasználók összes költése"
                );
        queryCB.setValue("Éves könyveladás");  // Default

        firstTC.setCellValueFactory(new PropertyValueFactory<>("firstValue"));
        secondTC.setCellValueFactory(new PropertyValueFactory<>("secondValue"));

        queryCB.setOnAction(e -> handleQuerySelection());

        handleQuerySelection(); // Adatok betöltése

        cancelBTN.setOnAction(e -> {
            try {
                cancel();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // A ComboBox-ban kiválasztott elem alapján meghívja a megfelelő számú lekérdezést
    @FXML
    private void handleQuerySelection() {
        String selectedQuery = queryCB.getValue();
        List<QueryResult> books = statisticsDAO.getQueryResults(3);

        switch (selectedQuery) {
            case "Műfajonként hány könyv van":
                books = statisticsDAO.getQueryResults(1); break;
            case "Felhasználók rendelési száma":
                books = statisticsDAO.getQueryResults(2); break;
            case "TOP 5 legtöbbet rendelt könyv":
                books = statisticsDAO.getQueryResults(3); break;
            case "Törzsvásárlók (legalább 3 rendelés)":
                books = statisticsDAO.getQueryResults(4); break;
            case "Rendelések összértéke havonta":
                books = statisticsDAO.getQueryResults(5); break;
            case "Átlagos könyvár műfajonként":
                books = statisticsDAO.getQueryResults(6); break;
            case "Legtöbbet választott könyv":
                books = statisticsDAO.getQueryResults(7); break;
            case "Csak egy rendeléses felhasználók":
                books = statisticsDAO.getQueryResults(8); break;
            case "Felhasználók összes költése":
                books = statisticsDAO.getQueryResults(9); break;
        }

        queryTV.setItems(FXCollections.observableArrayList(books));
    }

    private void cancel() throws IOException {
        App.setRoot("list_books");
    }
}
