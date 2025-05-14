package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Invoice;
import hu.adatba.Service.InvoiceService;
import hu.adatba.Session;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    TableColumn<Invoice, Integer> invoiceidTC, dateTC, priceTC, titleTC, fullnameTC, addressTC;

    @FXML
    private ComboBox<String> queryCB;

    @FXML
    private Button getbackBTN;

    private final InvoiceService invoiceService = new InvoiceService();

    public InvoicesController() throws SQLException {
    }

    // Metódusok
    @FXML
    public void initialize() {
        if(Session.getUser().getRole().equals("admin")) {
            queryCB.setVisible(true);
        }

        queryCB.getItems().addAll("Bejelentkezett felhasználók", "Vendég felhasználók");
        queryCB.setValue("Bejelentkezett felhasználók");

        invoiceidTC.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
        dateTC.setCellValueFactory(new PropertyValueFactory<>("year"));
        priceTC.setCellValueFactory(new PropertyValueFactory<>("price"));
        titleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
        fullnameTC.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        addressTC.setCellValueFactory(new PropertyValueFactory<>("address"));

        queryCB.setOnAction(e -> myInvoicesToTable());

        myInvoicesToTable();


        getbackBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void myInvoicesToTable(){
        String selectedQuery = queryCB.getValue();
        List<Invoice> invoices = invoiceService.getAllInvoices("felhasznalo");

        switch (selectedQuery) {
            case "Bejelentkezett felhasználók": invoices = invoiceService.getAllInvoices("felhasznalo"); break;
            case "Vendég felhasználók": invoices = invoiceService.getAllInvoices("latogato"); break;
        }


        invoicelistTV.setItems(FXCollections.observableArrayList(invoices));

    }

    private void switchToListBooks() throws IOException {
        App.setRoot("list_books");
    }
}
