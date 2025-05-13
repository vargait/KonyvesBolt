package hu.adatba.Service;

import hu.adatba.DAO.GenreDAO;
import hu.adatba.Model.Book;
import hu.adatba.Model.Genre;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreService {
    private static final Logger logger = Logger.getLogger(GenreService.class.getName());

    private final GenreDAO genreDAO;

    public GenreService() throws SQLException {
        this.genreDAO = new GenreDAO();
    }

    //Műfaj hozzáadása
    public boolean addGenre(String GenreName, String SubGenreName) throws SQLException {
        Genre genre = new Genre(GenreName, SubGenreName);
        if(genreDAO.findGenreByGenreAndSubgenre(GenreName, SubGenreName) == null) {
            return genreDAO.insertGenre(genre);
        }
        logger.log(Level.INFO, "A konyv mar letezik");
        return false;
    }

    // Összes könyv lekérdezése
    public List<Genre> getAllGenres() {
        return genreDAO.getAllGenres();
    }


}
