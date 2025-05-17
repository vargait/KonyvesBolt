package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Book;
import hu.adatba.Model.Order;
import hu.adatba.Model.User;
import hu.adatba.Service.OrderService;
import hu.adatba.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class OrderController {
    // Adattagok
    @FXML
    private Text selectedBookT;

    @FXML
    private TextField fullnameTF, addressTF, creditnumberTF;

    @FXML
    private Button cancelBTN, orderBTN;

    @FXML
    private Label messageLabel;

    private final OrderService orderService = new OrderService();
    User user;
    Book selectedBook;

    public OrderController() throws SQLException {
    }

    // Metódusok


    @FXML
    private void switchToListBooks() throws IOException {
        Session.clearSelectedBook();
        App.setRoot("list_books");
    }


    /*
    @FXML
    public void initialize() {
        user = Session.getUser();
        selectedBook = Session.getSelectedBook();
        selectedBookT.setText(selectedBook.getAuthor() + " - " + selectedBook.getTitle());

        if(user.getRole().equals("felhasznalo")){
            fullnameTF.setText(user.getFullName());
            addressTF.setText(user.getPostalAddress());
            creditnumberTF.setText(user.getCreditNumber());
            fullnameTF.setDisable(true);
            creditnumberTF.setDisable(true);
        }

        cancelBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        orderBTN.setOnAction(e -> {
            try {
                handleOrder();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void switchToListBooks() throws IOException {
        Session.clearSelectedBook();
        App.setRoot("list_books");
    }

    private void handleOrder() throws IOException {
        String fullname = fullnameTF.getText();
        String address = addressTF.getText();
        String creditnumber = creditnumberTF.getText();

        if(fullname.isEmpty() || address.isEmpty() || creditnumber.isEmpty()){
            messageLabel.setText("Helytelen adatok!");
            return;
        }

        if(orderService.addOrder(new Order(user.getUserID(), totalPrice(), address))){
            messageLabel.setText("Sikeres rendelés");
        }
        else{
            messageLabel.setText("Sikertelen rendelés!");
        }
    }

    private int totalPrice() {
        return 1;
    }*/

}
