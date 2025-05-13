package hu.adatba.DAO;

import hu.adatba.Model.Genre;
import hu.adatba.db.DBConnect;

import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenreDAO {
    private static final Logger logger = Logger.getLogger(GenreDAO.class.getName());

    // Csatlakozás a DB-hez
    private final Connection conn;

    public GenreDAO() throws SQLException {
        this.conn = DBConnect.getConnection();
    }

    //Genre hozzáadása a DB-hez
    public boolean insertGenre(Genre genre) {
        String sql = "INSERT INTO MUFAJ (MUFAJNEV, ALMUFAJ) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genre.getGenreName());
            stmt.setString(2, genre.getSubGenreName());

            int rowsAdded = stmt.executeUpdate();
            if (rowsAdded > 0) {
                logger.log(Level.INFO, "Mufaj hozzaadasa DB-hez sikeres.");
                return true;
            } else {
                logger.log(Level.SEVERE, "Mufaj hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                return false;
            }
        } catch (SQLException ed) {
            logger.log(Level.SEVERE, "Mufaj hozzaadasa DB-hez sikertelen: ", ed);
        }
        return false;
    }

    // Műfaj lekérése
    private Genre getGenre(ResultSet rs) throws SQLException {
        Genre genre = new Genre(
                rs.getString("MUFAJNEV"),
                rs.getString("ALMUFAJ")

        );
        return genre;
    }
    // Összes műfaj lekérdezése
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * FROM MUFAJ ORDER BY MUFAJNEV";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                genres.add(getGenre(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return genres;
    }


    // Könyv lekérése DB-ből szerző és cím alapján
    public Genre findGenreByGenreAndSubgenre(String Genre, String Subgenre) {
        String sql = "SELECT * FROM MUFAJ WHERE MUFAJNEV = ? AND ALMUFAJ = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Genre);
            stmt.setString(2, Subgenre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Genre genre = getGenre(rs);
                    logger.log(Level.INFO, "Műfaj lekérése sikeres");
                    return genre;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Műfaj lekerese sikertelen: ", e);
        }
        return null;
    }


}

