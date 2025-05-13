package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.DAO.OrderDAO;
import hu.adatba.Model.QueryResult;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
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

    private final OrderDAO orderDAO = new OrderDAO();

    public StatisticsController() throws SQLException {
    }

    // Metódusok
    @FXML
    public void initialize() {
        queryCB.getItems().addAll("Törzsvásárlók rendeléseinek száma", "Felhasználónként összes elköltött pénz", "Akciós rendelések könyvcímekkel", "Éves könyveladás", "Műfajonként eladott könyvek száma");
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
        List<QueryResult> books = orderDAO.getQueryResults(4);

        switch (selectedQuery) {
            case "Törzsvásárlók rendeléseinek száma":
                books = orderDAO.getQueryResults(1); break;
            case "Felhasználónként összes elköltött pénz":
                books = orderDAO.getQueryResults(2); break;
            case "Akciós rendelések könyvcímekkel":
                books = orderDAO.getQueryResults(3); break;
            case "Éves könyveladás":
                books = orderDAO.getQueryResults(4); break;
            case "Műfajonként eladott könyvek száma":
                books = orderDAO.getQueryResults(5); break;
        }

        queryTV.setItems(FXCollections.observableArrayList(books));
    }

    private void cancel() throws IOException {
        App.setRoot("list_books");
    }
}
