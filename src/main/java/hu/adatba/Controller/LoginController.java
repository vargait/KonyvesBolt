package hu.adatba.Controller;

import java.io.IOException;
import java.sql.SQLException;

import hu.adatba.App;
import hu.adatba.Model.User;
import hu.adatba.Service.UserService;
import hu.adatba.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    // Adattagok
    @FXML
    private TextField loginuserTF;

    @FXML
    private PasswordField loginpwPF;

    @FXML
    private Button loginBTN;

    @FXML
    private Button registerBTN;

    @FXML
    private Label messageLabel;

    private final UserService userService = new UserService();

    public LoginController() throws SQLException {
    }

    // Metódusok
    @FXML
    public void initialize() {
        loginBTN.setOnAction(e -> {
            try {
                handleLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        registerBTN.setOnAction(e -> {
            try {
                switchToRegister();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleLogin() throws IOException {
        String username = loginuserTF.getText().trim();
        String password = loginpwPF.getText().trim();

        User user = userService.loginUser(username, password);
        if (user != null) {
            Session.setUser(user);
            messageLabel.setText("Sikeres bejelentkezés!");
            App.setRoot("list_books");
        } else {
            messageLabel.setText("Helytelen adatok!");
        }
    }

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register");
    }
}
