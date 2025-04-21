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
    private TextField regemailTF;

    @FXML
    private PasswordField regpwPF;

    @FXML
    private PasswordField regpwagainPF;

    @FXML
    private Button registerBTN;

    @FXML
    private Button loginBTN;

    @FXML
    private RadioButton regcreditRadio;

    @FXML
    private RadioButton regcashRadio;

    private ToggleGroup paymentMethodGroup;

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

        paymentMethodGroup = new ToggleGroup();

        regcreditRadio.setToggleGroup(paymentMethodGroup);
        regcashRadio.setToggleGroup(paymentMethodGroup);
    }

    private void handleRegister() throws IOException {
        String username = reguserTF.getText().trim();
        String email = regemailTF.getText().trim();
        String password = regpwPF.getText().trim();
        String passwordConfirm = regpwagainPF.getText().trim();
        String paymentMethod;
        RadioButton selected = (RadioButton) paymentMethodGroup.getSelectedToggle();

        if(selected != null){
            paymentMethod = selected.getText();
        }
        else{
            messageLabel.setText("Nincs fizetési mód kiválasztva!");
            return;
        }

        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || paymentMethod.isEmpty()) {
            messageLabel.setText("Minden mezőt ki kell tölteni!");
            return;
        }
        if(!password.equals(passwordConfirm)) {
            messageLabel.setText("Nem egyeznek a jelszavak!");
            return;
        }

        if (userService.registerUser(username, email, password, paymentMethod)) {
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