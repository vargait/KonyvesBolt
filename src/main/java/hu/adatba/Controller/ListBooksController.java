package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.User;
import hu.adatba.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ListBooksController {
    // Adattagok
    @FXML
    private Button editprofileBTN;

    @FXML
    private Button addbooksBTN;

    @FXML
    private Button logoutBTN;

    // Metódusok
    @FXML
    public void initialize() {
        User user = Session.getUser();
        if (user != null) {
            if(user.getRole().equals("admin")) {
                addbooksBTN.setVisible(true);
            }
        }

        logoutBTN.setOnAction(e -> {
            try {
                logout();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @FXML
    private void switchToAddBooks() throws IOException {
        App.setRoot("add_books");
    }

    @FXML
    private void switchToEditProfile() throws IOException {
        App.setRoot("useredit");
    }

    @FXML
    private void logout() throws IOException {
        Session.clear();
        App.setRoot("login");
    }
}
