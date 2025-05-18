package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Invoice;
import hu.adatba.Service.InvoiceService;
import hu.adatba.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InvoicesController {
    // Adattagok
    @FXML
    TableView<Invoice> invoicelistTV;

    @FXML
    TableColumn<Invoice, Integer> orderidTC,  dateTC,  creditnumberTC, countTC, totalpriceTC;

    @FXML
    TableColumn<Invoice, String> fullnameTC, addressTC;

    @FXML
    private Button getbackBTN;

    private final InvoiceService invoiceService = new InvoiceService();

    public InvoicesController() {
    }

    // Metódusok
    @FXML
    public void initialize() throws SQLException {
        orderidTC.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        fullnameTC.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        dateTC.setCellValueFactory(new PropertyValueFactory<>("date"));
        addressTC.setCellValueFactory(new PropertyValueFactory<>("address"));
        creditnumberTC.setCellValueFactory(new PropertyValueFactory<>("creditNumber"));
        countTC.setCellValueFactory(new PropertyValueFactory<>("bookCount"));
        totalpriceTC.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        List<Invoice> invoices = invoiceService.getAllInvoices(Session.getUser().getUserID());
        invoicelistTV.setItems(FXCollections.observableArrayList(invoices));


        getbackBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void switchToListBooks() throws IOException {
        App.setRoot("list_books");
    }
}
