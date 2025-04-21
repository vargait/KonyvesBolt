package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.User;
import hu.adatba.Service.UserService;
import hu.adatba.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class UserEditController {
    // Adattagok
    @FXML
    private TextField edituserTF;

    @FXML
    private TextField editemailTF;

    @FXML
    private PasswordField editpwPF;

    @FXML
    private PasswordField editpwagainPF;

    @FXML
    private Button saveBTN;

    @FXML
    private Button editcancelBTN;

    @FXML
    private Label messageLabel;

    private final UserService userService = new UserService();

    public UserEditController() throws SQLException {
    }

    // Metódosuok
    @FXML
    public void initialize() {
        User user = Session.getUser();
        if (user != null) {
            edituserTF.setText(user.getUsername());
            editemailTF.setText(user.getEmail());
        }

        saveBTN.setOnAction(e -> {
            try {
                handleSave();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        editcancelBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleSave() throws IOException {
        String username = edituserTF.getText().trim();
        String email = editemailTF.getText().trim();
        String password = editpwPF.getText().trim();
        String passwordConfirm = editpwagainPF.getText().trim();

        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            messageLabel.setText("Minden mezőt ki kell tölteni!");
            return;
        }
        if(!password.equals(passwordConfirm)) {
            messageLabel.setText("Nem egyeznek a jelszavak!");
            return;
        }

        User user = Session.getUser();
        if(user != null){
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);

            if (userService.updateUser(user)) {
                messageLabel.setText("Adatok sikeresen módosítva.");
            } else {
                messageLabel.setText("Az adatok módosítása sikertelen.");
            }
        }
    }

    @FXML
    private void switchToListBooks() throws IOException {
        App.setRoot("list_books");
    }
}
