package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

public class OrderController {
    // Adattagok
    @FXML
    private Text selectedBookT;
    @FXML
    private Button cancelBTN;

    // Metódusok
    @FXML
    public void initialize() {
        selectedBookT.setText(Session.getSelectedBook().getAuthor() + " - " + Session.getSelectedBook().getTitle());
        cancelBTN.setOnAction(e -> {
            try {
                switchToListBooks();
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
}
