package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Order;
import hu.adatba.Model.User;
import hu.adatba.Service.BookService;
import hu.adatba.Service.OrderService;
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
import java.util.logging.Logger;

public class MyOrdersController {

    private final OrderService orderService = new OrderService();
    private final User user;
    private static final Logger logger = Logger.getLogger(MyOrdersController.class.getName());

    @FXML
    TableView<Order> orderlistTV;

    @FXML
    TableColumn<Order, Integer> orderlistOID;

    @FXML
    TableColumn<Order, Integer> orderlistUID;

    @FXML
    TableColumn<Order, Integer> orderlistBID;

    @FXML
    private Button getbackBTN;

    public MyOrdersController() throws SQLException {
        user = Session.getUser();
    }

    public void initialize() {
        myOrdersToTable();


        getbackBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

    }

    private void myOrdersToTable(){
        orderlistOID.setCellValueFactory(new PropertyValueFactory<>("OrderID"));
        orderlistBID.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        orderlistUID.setCellValueFactory(new PropertyValueFactory<>("UserID"));

        List<Order> orders = orderService.getAllOrders();
        orderlistTV.setItems(FXCollections.observableArrayList(orders));

    }

    private void switchToListBooks() throws IOException{
        App.setRoot("list_books");
    }

}
