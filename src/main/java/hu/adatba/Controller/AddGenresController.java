package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Genre;
import hu.adatba.Service.GenreService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddGenresController {
    @FXML
    private TextField addgenresnameTF;

    @FXML
    private TextField addgenresecondaryTF;

    @FXML
    private Button addGenresmegseBTN;

    @FXML
    private Button addGenressubmitBTN;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Genre> listgenresTV;

    @FXML
    private TableColumn<Genre, String> listgenreTC;

    @FXML
    private TableColumn<Genre, String> listsubgenreTC;

    private final GenreService genreService = new GenreService();

    public AddGenresController() throws SQLException {
    }



    //Metódusok
    public void initialize(){

        listgenreTC.setCellValueFactory(new PropertyValueFactory<>("GenreName"));
        listsubgenreTC.setCellValueFactory(new PropertyValueFactory<>("SubGenreName"));

        List<Genre> genres = genreService.getAllGenres();
        listgenresTV.setItems(FXCollections.observableArrayList(genres));




        addGenresmegseBTN.setOnAction(e ->{
            try{
                switchToListBooks();

            }catch(IOException er) {
                throw new RuntimeException();
            }
        });
        addGenressubmitBTN.setOnAction(e ->{
            try{
                handleSave();
            }catch(IOException err) {
                throw new RuntimeException(err);
            }
        });
    }
    private void handleSave() throws IOException {
        try {
            // Input kezelés

            String addgenresname = addgenresnameTF.getText().trim();
            String addgenresecondary = addgenresecondaryTF.getText().trim();


            // Üres input kezelés
            if(addgenresname.isEmpty() || addgenresecondary.isEmpty()) {
                messageLabel.setText("Helytelen adatok!");
                return;
            }

            // Felvitel
            if(genreService.addGenre(addgenresname, addgenresecondary)){
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
        Stage stage = (Stage) addGenresmegseBTN.getScene().getWindow();
        stage.close();
    }
}



