package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Genre;
import hu.adatba.Service.BookService;
import hu.adatba.Service.GenreService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class AddBooksController {
    // Adattagok
    @FXML
    private TextField addbooktitleTF, addbookauthorTF, addbookpublisherTF, addbookbindingTF, addbookpagesTF, addbookpriceTF;

    @FXML
    private Spinner<String> addbookgenreSP, addbooksubgenreSP, addbooksizeSP, addbookebookSP;

    @FXML
    private Spinner<Integer> addbookyearSP;

    @FXML
    private Button addbookpostBTN, addbookcancelBTN;

    @FXML
    private Label messageLabel;

    private final BookService bookService = new BookService();
    private final GenreService genreService = new GenreService();

    public AddBooksController() throws SQLException {
    }


    // Metódusok
    @FXML
    public void initialize() {
        // Könyv megjelenési éve Spinner
        int currentYear = LocalDate.now().getYear();
        addbookyearSP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1870, currentYear, currentYear));

        // Könyv méret Spinner
        ObservableList<String> options = FXCollections.observableArrayList("Normál", "Nagy", "Közepes", "Kicsi", "E-Könyv");
        SpinnerValueFactory<String> valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        valueFactory.setValue("Normál");
        addbooksizeSP.setValueFactory(valueFactory);

        // Ebook-e Spinner
        options = FXCollections.observableArrayList("Nem", "Igen");
        valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        valueFactory.setValue("Nem");
        addbookebookSP.setValueFactory(valueFactory);

        // Műfaj Spinner
        options = FXCollections.observableArrayList(genreService.getGenres());
        valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        addbookgenreSP.setValueFactory(valueFactory);

        // Alműfaj Spinner
        addbookgenreSP.valueProperty().addListener((obs, oldGenre, newGenre) -> {
            List<String> subgenres = genreService.getSubGenres(newGenre);
            ObservableList<String> subgenreOptions = FXCollections.observableArrayList(subgenres);
            SpinnerValueFactory<String> subgenreFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(subgenreOptions);
            subgenreFactory.setValue(subgenres.isEmpty() ? "" : subgenres.get(0));
            addbooksubgenreSP.setValueFactory(subgenreFactory);
        });

        // Alapértelmezett alműfaj beállítása az első műfaj alapján
        List<String> genres = genreService.getGenres();
        if (!genres.isEmpty()) {
            List<String> initialSubgenres = genreService.getSubGenres(genres.get(0));
            SpinnerValueFactory<String> initialSubgenreFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList(initialSubgenres));
            initialSubgenreFactory.setValue(initialSubgenres.isEmpty() ? "" : initialSubgenres.get(0));
            addbooksubgenreSP.setValueFactory(initialSubgenreFactory);
        }

        // Gombok
        addbookpostBTN.setOnAction(e -> {
            try {
                handleSave();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        addbookcancelBTN.setOnAction(e -> {
            try {
                switchToListBooks();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleSave() throws IOException {
        try {
            // Input kezelés
            int PublicationYear = addbookyearSP.getValue();
            String Genre = addbookgenreSP.getValue();
            Genre genre = genreService.getGenreByGenreAndSubgenre(addbookgenreSP.getValue(), addbooksubgenreSP.getValue());
            String Size = addbooksizeSP.getValue();
            String Publisher = addbookpublisherTF.getText().trim();
            String Author = addbookauthorTF.getText().trim();
            String Title = addbooktitleTF.getText().trim();
            String Binding = addbookbindingTF.getText().trim();
            int Pages = Integer.parseInt(addbookpagesTF.getText().trim());
            int Price = Integer.parseInt(addbookpriceTF.getText().trim());
            int Ebook = 0;
            if (Objects.equals(addbookebookSP.getValue(), "Igen")) {
                Ebook = 1;
            }

            // Üres input kezelés
            if(Publisher.isEmpty() || Author.isEmpty() || Title.isEmpty() || Genre == null || Binding.isEmpty() || Price < 0 || Pages < 1) {
                messageLabel.setText("Helytelen adatok!");
                return;
            }

            // Felvitel
            if(bookService.addBook(PublicationYear, Publisher, Author, Title, genre.getGenreID(), Pages, Ebook, Binding, Price, Size)){
                messageLabel.setText("Sikeres felvitel!");
            }
            else{
                messageLabel.setText("Sikertelen felvitel!");
            }
        } catch (RuntimeException | SQLException e) {
            messageLabel.setText("Sikertelen mentés!");
        }
    }

    @FXML
    private void switchToListBooks() throws IOException {
        App.setRoot("list_books");
    }
}
