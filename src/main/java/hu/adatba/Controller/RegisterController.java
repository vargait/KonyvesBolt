package hu.adatba.Controller;

import java.io.IOException;
import java.sql.SQLException;

import hu.adatba.App;
import hu.adatba.Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {
    // Adattagok
    @FXML
    private TextField reguserTF;

    @FXML
    private TextField regnameTF;

    @FXML
    private TextField regemailTF;

    @FXML
    private TextField regpostaladdressTF;

    @FXML
    private TextField regcreditnumberTF;

    @FXML
    private PasswordField regpwPF;

    @FXML
    private PasswordField regpwagainPF;

    @FXML
    private Button registerBTN;

    @FXML
    private Button loginBTN;

    @FXML
    private Label messageLabel;

    private final UserService userService = new UserService();

    public RegisterController() throws SQLException {
    }


    // Metódusok
    @FXML
    public void initialize() {
        registerBTN.setOnAction(e -> {
            try {
                handleRegister();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginBTN.setOnAction(e -> {
            try {
                switchToLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleRegister() throws IOException {
        String username = reguserTF.getText().trim();
        String fullName = regnameTF.getText().trim();
        String email = regemailTF.getText().trim();
        String postalAddress = regpostaladdressTF.getText().trim();
        String creditNumber = regcreditnumberTF.getText().trim();
        String password = regpwPF.getText().trim();
        String passwordConfirm = regpwagainPF.getText().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || fullName.isEmpty() || postalAddress.isEmpty() || creditNumber.isEmpty()) {
            messageLabel.setText("Minden mezőt ki kell tölteni!");
            return;
        }
        if(!password.equals(passwordConfirm)) {
            messageLabel.setText("Nem egyeznek a jelszavak!");
            return;
        }

        if (userService.registerUser(username, email, password, fullName, postalAddress, creditNumber)) {
            messageLabel.setText("Sikeres regisztráció!");
        } else {
            messageLabel.setText("Sikertelen regisztráció!");
        }
    }

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

}