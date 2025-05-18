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

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private User user;
    private Book selectedBook;
    private Store selectedStore;
    private Cart usedCart;
    private int selectedStock;

    private static final Logger logger = Logger.getLogger(AddToCartController.class.getName());


    public AddToCartController() throws SQLException {
    }

    // Metódusok
    @FXML
    public void initialize() {
        user = Session.getUser();
        if(user.getRole().equals("felhasznalo")) {
            cartService.addCart(Session.getUser().getUserID(), 2025);
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
        App.setRoot("list_books");
    }

    private void putItemtoCartStock() throws IOException, SQLException{
        CartStockService cartStockService = new CartStockService();
        CartStock cartStock = cartStockService.findCartStockByCartIDAndBookID(usedCart.getKosarID(), selectedBook.getBookID());
        if(selectedStore != null || cartStock != null){
            if(cartStockService.addCartStock(usedCart.getKosarID(),selectedBook.getBookID(), stockSP.getValue(), selectedBook.getPrice())){
                messageLabel.setText("Sikeres kosárba rakás!");
                int newStock = selectedStock - stockSP.getValue();
                if(storeStockService.updateStock(newStock, selectedStore.getStoreID(), selectedBook.getBookID())){
                    logger.log(Level.INFO, "Készlet változtatása sikeres");
                } else{
                    logger.log(Level.SEVERE, "Készlet változtatása sikertelen");
                }
            }else {
                messageLabel.setText("Hozzáadás sikertelen");
            }
        }
        else{
            messageLabel.setText("Válasszon ki egy áruházat!");
        }
    }

/*
    private void putItemToCart() throws IOException, SQLException {
        CartService cartService = new CartService();
        Cart cart = cartService.findCartByUserID(user.getUserID());
        if(selectedStore != null || cart != null) {
            if(cartService.insertBook(cart.getKosarID(), user.getUserID(), stockSP.getValue(), selectedBook.getPrice())){
                messageLabel.setText("Sikeres hozzáadás");
            }
            else{
                messageLabel.setText("Hozzáadás sikertelen.");
            }
        }
        else{
            messageLabel.setText("Válasszon ki egy áruházat!");
        }
    }

 */
}
