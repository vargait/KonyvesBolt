package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.DAO.CartDAO;
import hu.adatba.Model.Cart;
import hu.adatba.Model.Order;
import hu.adatba.Service.CartService;
import hu.adatba.Service.OrderService;
import hu.adatba.Service.UserService;
import hu.adatba.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyOrdersController {

    private final OrderService orderService = new OrderService();
    private final CartService cartService = new CartService();

    @FXML
    TableView<Cart> myTV;

    @FXML
    TableColumn<Cart, Integer> myCartID, myUserID, myDate;

    @FXML
    private ComboBox<String> queryCB;

    @FXML
    private Button getbackBTN, gimmeCartBTN;

    public MyOrdersController() throws SQLException {
    }

    //Metódusok

    public void initialize() {
        //Saját Cartom listázása
        myCartID.setCellValueFactory(new PropertyValueFactory<>("kosarID"));
        myUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        myDate.setCellValueFactory(new PropertyValueFactory<>("letrehozas_ev"));

        List<Cart> carts = cartService.getMyCarts(Session.getUser().getUserID());
        myTV.setItems(FXCollections.observableArrayList(carts));


        if(Session.getUser().getRole().equals("admin")) {
            queryCB.setVisible(true);
        }

        queryCB.getItems().addAll("Bejelentkezett felhasználók", "Vendég felhasználók");
        queryCB.setValue("Bejelentkezett felhasználók");

        getbackBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void switchToListBooks() throws IOException{
        App.setRoot("list_books");
    }


    private void gimmeCart(){

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
