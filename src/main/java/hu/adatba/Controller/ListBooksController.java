package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Book;
import hu.adatba.Model.User;
import hu.adatba.Service.BookService;
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

public class ListBooksController {
    // Adattagok
    @FXML
    private TableView<Book> listbooksTV;

    @FXML
    private TableColumn<Book, String> listtitleTC;

    @FXML
    private TableColumn<Book, String> listauthorTC;

    @FXML
    private TableColumn<Book, String> listpublisherTC;

    @FXML
    private TableColumn<Book, String> listgenreTC;

    @FXML
    private TableColumn<Book, String> listsubgenreTC;

    @FXML
    private TableColumn<Book, String> listpublishyearTC;

    @FXML
    private TableColumn<Book, String> listpagesTC;

    @FXML
    private TableColumn<Book, String> listbindingTC;

    @FXML
    private TableColumn<Book, String> listebookTC;

    @FXML
    private TableColumn<Book, String> listsizeTC;

    @FXML
    private TableColumn<Book, String> listavailableamountTC;

    @FXML
    private TableColumn<Book, String> listpriceTC;

    @FXML
    private Button editprofileBTN;

    @FXML
    private Button addbooksBTN;

    @FXML
    private Button logoutBTN;

    @FXML Button addgenresBTN;

    private final BookService bookService = new BookService();

    public ListBooksController() throws SQLException {
    }

    // Metódusok
    @FXML
    public void initialize() {
        listtitleTC.setCellValueFactory(new PropertyValueFactory<>("title"));
        listauthorTC.setCellValueFactory(new PropertyValueFactory<>("author"));
        listpublisherTC.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        listgenreTC.setCellValueFactory(new PropertyValueFactory<>("genre"));
        listsubgenreTC.setCellValueFactory(new PropertyValueFactory<>("subGenre"));
        listpublishyearTC.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        listpagesTC.setCellValueFactory(new PropertyValueFactory<>("pages"));
        listbindingTC.setCellValueFactory(new PropertyValueFactory<>("binding"));
        listebookTC.setCellValueFactory(new PropertyValueFactory<>("ebook"));
        listsizeTC.setCellValueFactory(new PropertyValueFactory<>("size"));
        listavailableamountTC.setCellValueFactory(new PropertyValueFactory<>("availableAmount"));
        listpriceTC.setCellValueFactory(new PropertyValueFactory<>("price"));

        List<Book> books = bookService.getAllBooks();
        listbooksTV.setItems(FXCollections.observableArrayList(books));

        User user = Session.getUser();
        if (user != null) {
            if(user.getRole().equals("admin")) {
                addbooksBTN.setVisible(true);
                addgenresBTN.setVisible(true);
            }
            if(!user.getRole().equals("latogato")) {
                editprofileBTN.setVisible(true);

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
    @FXML
    private void switchToAddGenres() throws IOException{
        App.setRoot("add_genres");
    }
}
