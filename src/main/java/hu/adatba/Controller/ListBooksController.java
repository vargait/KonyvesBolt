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
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private final BookService bookService = new BookService();
    private final User user;
    private static final Logger logger = Logger.getLogger(ListBooksController.class.getName());

    public ListBooksController() throws SQLException {
        user = Session.getUser();
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
        addActionColumn();

        List<Book> books = bookService.getAllBooks();
        listbooksTV.setItems(FXCollections.observableArrayList(books));

        if (user != null) {
            if(user.getRole().equals("admin")) {
                addbooksBTN.setVisible(true);
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

    private void addActionColumn() {
        TableColumn<Book, Void> listActionTC = new TableColumn<>("Művelet");
        if(!user.getRole().equals("admin")){
            listActionTC.setCellFactory(param -> new TableCell<>() {

                private final Button orderBtn = new Button("Rendelés");
                {
                    orderBtn.setOnAction(event -> {
                        Book book = getTableView().getItems().get(getIndex());
                        try {
                            switchToOrder(book);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : orderBtn);
                }
            });
        }
        else{
            listActionTC.setCellFactory(param -> new TableCell<>() {

                private final Button editBtn = new Button("Szerkesztés");
                /*
                private final Button deleteBtn = new Button("Törlés");
                private final HBox buttonBox = new HBox(5, editBtn, deleteBtn);
                */
                {
                    editBtn.setOnAction(event -> {
                        Book book = getTableView().getItems().get(getIndex());
                        try {
                            switchToEdit(book);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

                /*
                {
                    deleteBtn.setOnAction(event -> {
                        Book book = getTableView().getItems().get(getIndex());
                        try {
                            if(bookService.deleteBook(book.getBookID())){
                                logger.log(Level.INFO, "Könyv sikeresen törölve");
                                getTableView().getItems().remove(book);
                            }
                            else{
                                logger.log(Level.INFO, "Könyv törlése sikertelen");
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });

                }
                */

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : editBtn);
                }
            });
        }

        listbooksTV.getColumns().add(listActionTC);
    }

    private void switchToOrder(Book book) throws IOException {
        Session.setSelectedBook(book);
        App.setRoot("order");
    }

    private void switchToEdit(Book book) throws IOException {
        Session.setSelectedBook(book);
        App.setRoot("edit_book");
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
