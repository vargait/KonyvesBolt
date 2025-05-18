package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Book;
import hu.adatba.Model.Genre;
import hu.adatba.Service.BookService;
import hu.adatba.Service.GenreService;
import hu.adatba.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class EditBookController {
    // Adattagok
    @FXML
    private TextField editbooktitleTF, editbookauthorTF, editbookpublisherTF, editbookbindingTF, editbookpagesTF, editbookpriceTF;

    @FXML
    private Spinner<String> editbookgenreSP;

    @FXML
    private Spinner<String> editbooksubgenreSP;

    @FXML
    private Spinner<Integer> editbookyearSP;

    @FXML
    private Spinner<String> editbooksizeSP;

    @FXML
    private Spinner<String> editbookebookSP;

    @FXML
    private Button editbookpostBTN;

    @FXML
    private Button editbookcancelBTN;

    @FXML
    private Label messageLabel;

    private final BookService bookService = new BookService();
    private final GenreService genreService = new GenreService();

    public EditBookController() throws SQLException {
    }

    // Metódusok
    @FXML
    public void initialize() {
        // TextField adatok
        editbooktitleTF.setText(Session.getSelectedBook().getTitle());
        editbookauthorTF.setText(Session.getSelectedBook().getAuthor());
        editbookpublisherTF.setText(Session.getSelectedBook().getPublisher());
        editbookbindingTF.setText(Session.getSelectedBook().getBinding());
        editbookpagesTF.setText(String.valueOf(Session.getSelectedBook().getPages()));
        editbookpriceTF.setText(String.valueOf(Session.getSelectedBook().getPrice()));

        // Könyv megjelenési éve Spinner
        int currentYear = LocalDate.now().getYear();
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactoryDate = new SpinnerValueFactory.IntegerSpinnerValueFactory(1870, currentYear, currentYear);
        valueFactoryDate.setValue(Session.getSelectedBook().getPublicationYear());
        editbookyearSP.setValueFactory(valueFactoryDate);

        // Könyv méret Spinner
        ObservableList<String> options = FXCollections.observableArrayList("Normál", "Nagy", "Közepes", "Kicsi", "E-Könyv");
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        valueFactory.setValue(Session.getSelectedBook().getSize());
        editbooksizeSP.setValueFactory(valueFactory);

        // Ebook-e Spinner
        options = FXCollections.observableArrayList("Nem", "Igen");
        valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        valueFactory.setValue(Session.getSelectedBook().getEbook());
        editbookebookSP.setValueFactory(valueFactory);

        // Műfaj spinnerek
        List<String> genres = genreService.getGenres();
        SpinnerValueFactory.ListSpinnerValueFactory<String> genreFactory =
                new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(genres));
        genreFactory.setValue(Session.getSelectedBook().getGenre().getGenreName());
        editbookgenreSP.setValueFactory(genreFactory);

        setupSubgenreSpinner(genreFactory.getValue());

        editbookgenreSP.valueProperty().addListener((obs, oldGenre, newGenre) -> {
            setupSubgenreSpinner(newGenre);
        });



        // Gombok
        editbookpostBTN.setOnAction(e -> {
            try {
                handleSave();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        editbookcancelBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void setupSubgenreSpinner(String selectedGenre) {
        List<String> subgenres = genreService.getSubGenres(selectedGenre);
        ObservableList<String> subgenreOptions = FXCollections.observableArrayList(subgenres);

        SpinnerValueFactory.ListSpinnerValueFactory<String> subgenreFactory =
                (SpinnerValueFactory.ListSpinnerValueFactory<String>) editbooksubgenreSP.getValueFactory();

        if (subgenreFactory == null) {
            subgenreFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(subgenreOptions);
            editbooksubgenreSP.setValueFactory(subgenreFactory);
        } else {
            subgenreFactory.setItems(subgenreOptions);
        }

        String targetSub = Session.getSelectedBook().getGenre().getSubGenreName();
        if (subgenres.contains(targetSub)) {
            subgenreFactory.setValue(targetSub);
        } else {
            subgenreFactory.setValue(subgenres.isEmpty() ? "" : subgenres.get(0));
        }
    }

    private void handleSave() throws IOException {
        try {
            // Input kezelés
            int PublicationYear = editbookyearSP.getValue();
            Genre genre = genreService.getGenreByGenreAndSubgenre(editbookgenreSP.getValue(), editbooksubgenreSP.getValue());
            String Size = editbooksizeSP.getValue();
            String Publisher = editbookpublisherTF.getText().trim();
            String Author = editbookauthorTF.getText().trim();
            String Title = editbooktitleTF.getText().trim();
            String Binding = editbookbindingTF.getText().trim();
            int Pages = Integer.parseInt(editbookpagesTF.getText().trim());
            int Price = Integer.parseInt(editbookpriceTF.getText().trim());
            int Ebook = 0;
            if (Objects.equals(editbookebookSP.getValue(), "Igen")) {
                Ebook = 1;
            }

            // Üres input kezelés
            if(Publisher.isEmpty() || Author.isEmpty() || Title.isEmpty() || Binding.isEmpty() || Price < 0 || Pages < 1) {
                messageLabel.setText("Helytelen adatok!");
                return;
            }

            // Módosítás
            Book book = new Book(PublicationYear, Publisher, Author, Title, genre.getGenreID(), Pages, Ebook, Binding, Price, Size);
            book.setBookID(Session.getSelectedBook().getBookID());
            if(bookService.updateBook(book)){
                messageLabel.setText("Sikeres módosítás!");
            }
            else{
                messageLabel.setText("Sikertelen módosítás!");
            }
        } catch (RuntimeException e) {
            messageLabel.setText("Sikertelen mentés!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void switchToListBooks() throws IOException {
        Session.clearSelectedBook();
        App.setRoot("list_books");
    }
}
