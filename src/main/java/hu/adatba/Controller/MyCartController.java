package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.*;
import hu.adatba.Service.*;
import hu.adatba.Session;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyCartController {

    private final CartService cartService = new CartService();
    private final CartStockService cartStockService = new CartStockService();
    private final OrderService orderService = new OrderService();
    private  Cart usedCart;


    @FXML
    private Label messageLabel;
    @FXML
    TableView<Cart> myTV;

    @FXML
    TableColumn<Cart, Integer> myCartID, myUserID, myDate;

    @FXML
    TextField billingAddressTF, userNameTF, cardNumberTF;
    @FXML
    Text  text1, text2, text3;
    @FXML
    TableView<MyCartItem> myStockTV;
    @FXML
    TableColumn<MyCartItem, Integer> cartitemAmountTC, cartitemPriceTC, cartitemTotalTC;
    @FXML
    TableColumn<MyCartItem, String> cartitemAuthorTC, cartitemTitleTC;

    private static final Logger logger = Logger.getLogger(MyCartController.class.getName());

    @FXML
    private Button getbackBTN, handleOrderBTN;

    public MyCartController() throws SQLException {
    }
    //Metódusok

    public void initialize() {

        if(Session.getUser().getRole().equals("admin")) {
            // Összes cart listázása
            myCartID.setCellValueFactory(new PropertyValueFactory<>("kosarID"));
            myUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            myDate.setCellValueFactory(new PropertyValueFactory<>("letrehozas_ev"));
            addActionColumn();

            List<Cart> carts = cartService.getAllCarts();
            myTV.setItems(FXCollections.observableArrayList(carts));
            myTV.setVisible(true);
            myStockTV.setVisible(false);
            handleOrderBTN.setVisible(false);
            billingAddressTF.setVisible(false);
            userNameTF.setVisible(false);
            cardNumberTF.setVisible(false);
            text1.setVisible(false);
            text2.setVisible(false);
            text3.setVisible(false);
        }
        else{
            //Saját CartStockjaim listázása
            usedCart = cartService.findCartByUserID(Session.getUser().getUserID());
            int myCartID = usedCart.getKosarID();
            cartitemAuthorTC.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getBook().getAuthor())
            );
            cartitemTitleTC.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getBook().getTitle())
            );
            cartitemPriceTC.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getCartStock().getPrizeEach()).asObject()
            );
            cartitemAmountTC.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getCartStock().getQuantity()).asObject()
            );
            cartitemTotalTC.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getTotalPrice()).asObject()
            );
            addActionColumn();

            List<MyCartItem> cartItems = cartStockService.getMyCartStocks(myCartID);
            myStockTV.setItems(FXCollections.observableArrayList(cartItems));

            User user = Session.getUser();
            billingAddressTF.setText(user.getPostalAddress());
            userNameTF.setText(user.getFullName());
            cardNumberTF.setText(user.getCreditNumber());
        }

        getbackBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        handleOrderBTN.setOnAction(e -> {
            try {
                handleRendeles();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void addActionColumn() {
        if(Session.getUser().getRole().equals("admin")) {
            TableColumn<Cart, Void> listActionTC = new TableColumn<>("Művelet");

            listActionTC.setCellFactory(param -> new TableCell<>() {
                private final Button deletecartBtn = new Button("Törlés");

                {
                    deletecartBtn.setOnAction(event -> {
                        Cart cart = getTableView().getItems().get(getIndex());
                        if (cartService.deleteCartByUserID(cart.getUserID())) {
                            logger.log(Level.INFO, "Kosár sikeresen törölve");
                            getTableView().getItems().remove(cart);
                        } else {
                            logger.log(Level.INFO, "Kosár törlése sikertelen");
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deletecartBtn);
                    }
                }
            });

            myTV.getColumns().add(listActionTC);
        }
        else{
            TableColumn<MyCartItem, Void> listActionTC = new TableColumn<>("Művelet");
            listActionTC.setCellFactory(param -> new TableCell<>() {
                private final Button deleteitemBtn = new Button("Törlés");
                {
                    deleteitemBtn.setOnAction(event -> {
                        MyCartItem cartItem = getTableView().getItems().get(getIndex());
                        if(cartStockService.deleteFromCart(cartItem.getCartStock().getCartID(), cartItem.getCartStock().getBookID())){
                            logger.log(Level.INFO, "Kosárból sikeresen törölve");
                            getTableView().getItems().remove(cartItem);
                        }
                        else{
                            logger.log(Level.INFO, "Kosárból törlés sikertelen");
                        }
                    });

                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : deleteitemBtn);
                }
            });
            myStockTV.getColumns().add(listActionTC);
        }

    }

    private void switchToListBooks() throws IOException{
        App.setRoot("list_books");
    }

    private void handleRendeles() throws IOException {
        try {
            int sum = 0;
            for (int i = 0; i < myStockTV.getItems().size(); i++) {
                sum += myStockTV.getItems().get(i).getTotalPrice();
            }
            String billing_Address = billingAddressTF.getText().trim();
            String userName = userNameTF.getText().trim();
            String card_Number = cardNumberTF.getText().trim();

            //Üres input kezelés
            if (billing_Address.isEmpty() || userName.isEmpty() || card_Number.isEmpty()) {
                messageLabel.setText("Helytelen adatok!");
                return;
            }
            if (orderService.addOrder(Session.getUser().getUserID(), 2025, sum, billing_Address, userName, card_Number)) {
                messageLabel.setText("Sikeres rendeles!");
                if(Session.getUser().getRole().equals("felhasznalo")){
                    cartService.deleteCartByUserID(Session.getUser().getUserID());
                } else if(Session.getUser().getRole().equals("latogato")){
                    cartService.deleteCartByUserID(0);
                }
                switchToListBooks();
            } else {
                messageLabel.setText("Sikertelen rendeles!");
            }
        }catch(RuntimeException e){
            messageLabel.setText("Sikertelen rendeles! sql/runtim");
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /*

    public void initialize() {
        if(Session.getUser().getRole().equals("admin")) {
            queryCB.setVisible(true);
        }

        queryCB.getItems().addAll("Bejelentkezett felhasználók", "Vendég felhasználók");
        queryCB.setValue("Bejelentkezett felhasználók");

        orderidTC.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        discountedTC.setCellValueFactory(new PropertyValueFactory<>("discounted"));
        usernameTC.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        titleTC.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));

        queryCB.setOnAction(e -> myOrdersToTable());

        myOrdersToTable();


        getbackBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    @FXML
    private void myOrdersToTable(){
        String selectedQuery = queryCB.getValue();
        List<Order> orders = orderService.getAllOrders("felhasznalo");


        switch (selectedQuery) {
            case "Bejelentkezett felhasználók": orders = orderService.getAllOrders("felhasznalo"); break;
            case "Vendég felhasználók": orders = orderService.getAllOrders("latogato"); break;
        }


        orderlistTV.setItems(FXCollections.observableArrayList(orders));

    }

    private void switchToListBooks() throws IOException{
        App.setRoot("list_books");
    }
*/
}
