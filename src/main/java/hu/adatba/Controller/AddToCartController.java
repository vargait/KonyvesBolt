package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.*;
import hu.adatba.Service.CartService;
import hu.adatba.Service.CartStockService;
import hu.adatba.Service.StoreService;
import hu.adatba.Service.StoreStockService;
import hu.adatba.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddToCartController {
    // Adattagok
    @FXML
    private Text selectedBookT, selectedStoreT, stockT;

    @FXML
    private Button cancelBTN, additemBTN;

    @FXML
    private TableView<Store> liststoresTV;

    @FXML
    private TableColumn<Store, String> listnameTC, listaddressTC, listphoneTC, listemailTC;

    @FXML
    private Spinner<Integer> stockSP;

    @FXML
    private Label messageLabel;

    private final StoreService storeService = new StoreService();
    private final StoreStockService storeStockService = new StoreStockService();
    private final CartService cartService = new CartService();
    private Book selectedBook;
    private Store selectedStore;
    private Cart usedCart;
    private int selectedStock;

    public AddToCartController() throws SQLException {
    }

    // Metódusok
    @FXML
    public void initialize() {
        User user = Session.getUser();
        if(user.getRole().equals("felhasznalo")) {
            cartService.addCart(Session.getUser().getUserID());
        } else if(user.getRole().equals("latogato")) {
            cartService.addGuestCart();
        }


        selectedBook = Session.getSelectedBook();
        selectedBookT.setText(selectedBook.getAuthor() + " - " + selectedBook.getTitle() + " készlet:");
        usedCart = cartService.findCartByUserID(user.getUserID());

        listnameTC.setCellValueFactory(new PropertyValueFactory<>("storeName"));
        listaddressTC.setCellValueFactory(new PropertyValueFactory<>("storeAddress"));
        listphoneTC.setCellValueFactory(new PropertyValueFactory<>("storePNumber"));
        listemailTC.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<Store> stores = storeService.getStoresWithBook(Session.getSelectedBook().getBookID());
        if (!stores.isEmpty()) {
            selectedStore = stores.get(0);
        }
        addActionColumn();
        liststoresTV.setItems(FXCollections.observableArrayList(stores));


        cancelBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        additemBTN.setOnAction(e -> {
            try {
                putItemtoCartStock();
            } catch (IOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void addActionColumn() {
        TableColumn<Store, Void> listActionTC = new TableColumn<>("Művelet");
        listActionTC.setCellFactory(param -> new TableCell<>() {
            private final Button selectstoreBtn = new Button("Kiválasztás");
            {
                selectstoreBtn.setOnAction(event -> {
                    Store store = getTableView().getItems().get(getIndex());
                    selectedStore = new Store(store);
                    selectedStoreT.setText("Kiválasztott áruház: " + selectedStore.getStoreName());

                    // Készlet
                    selectedStock = 0;
                    try {
                        selectedStock = storeStockService.getStockByStoreAndBook(selectedStore.getStoreID(), selectedBook.getBookID()).getOnStock();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    // Darabszám kiválasztása 1-készlet
                    List<Integer> count = new ArrayList<>();
                    for (int i = 1; i <= selectedStock; i++) {
                        count.add(i);
                    }
                    ObservableList<Integer> options = FXCollections.observableArrayList(count);
                    SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
                    stockSP.setValueFactory(valueFactory);
                    stockT.setText(selectedStock + " db készleten.");
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : selectstoreBtn);
            }
        });
        liststoresTV.getColumns().add(listActionTC);
    }

    @FXML
    private void switchToListBooks() throws IOException {
        Session.clearSelectedBook();
        Stage stage = (Stage) cancelBTN.getScene().getWindow();
        stage.close();
    }

    private void putItemtoCartStock() throws IOException, SQLException{
        CartStockService cartStockService = new CartStockService();
        CartStock cartStock = cartStockService.findCartStockByCartIDAndBookID(usedCart.getKosarID(), selectedBook.getBookID());
        if(selectedStore != null || cartStock != null){
            if(cartStockService.addCartStock(usedCart.getKosarID(),selectedBook.getBookID(), stockSP.getValue(), selectedBook.getPrice())){
                messageLabel.setText("Sikeres kosárba rakás!");
            }else {
                messageLabel.setText("Hozzáadás sikertelen");
            }
        }
        else{
            messageLabel.setText("Válasszon ki egy áruházat!");
        }
    }
}
