package hu.adatba.DAO;

import hu.adatba.Model.Book;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO {
    private static final Logger logger = Logger.getLogger(BookDAO.class.getName());

    // Csatlakozás a DB-hez
    private final Connection conn;

    public BookDAO() throws SQLException {
        this.conn = DBConnect.getConnection();
    }

    // Könyv hozzáadása DB-hez
    public boolean insertBook(Book book) {
        String sql = "INSERT INTO KONYV (KIADAS_EVE, OLDALSZAM, DARAB, SZERZO, EKONYV, KOTES, AR, MERET, CIM, MUFAJNEV, ALMUFAJ, KIADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getPublicationYear());
            stmt.setInt(2, book.getPages());
            stmt.setInt(3, book.getAvailableAmount());
            stmt.setString(4, book.getAuthor());
            int ebook = 0;
            if (book.getEbook().equals("Igen")) { ebook = 1;}
            stmt.setInt(5, ebook);
            stmt.setString(6, book.getBinding());
            stmt.setInt(7, book.getPrice());
            stmt.setString(8, book.getSize());
            stmt.setString(9, book.getTitle());
            stmt.setString(10, book.getGenre());
            stmt.setString(11, book.getSubGenre());
            stmt.setString(12, book.getPublisher());

            int rowsAdded = stmt.executeUpdate();
            if (rowsAdded > 0) {
                logger.log(Level.INFO, "Konyv hozzaadasa DB-hez sikeres.");
                return true;
            } else {
                logger.log(Level.SEVERE, "Konyv hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Konyv hozzaadasa DB-hez sikertelen: ", e);
        }
        return false;
    }

    // Könyv lekérése
    private Book getBook(ResultSet rs) throws SQLException {
        Book book = new Book(
                rs.getInt("KIADAS_EVE"),
                rs.getString("KIADO"),
                rs.getString("SZERZO"),
                rs.getString("CIM"),
                rs.getString("MUFAJNEV"),
                rs.getString("ALMUFAJ"),
                rs.getInt("OLDALSZAM"),
                rs.getInt("DARAB"),
                rs.getInt("EKONYV"),
                rs.getString("KOTES"),
                rs.getInt("AR"),
                rs.getString("MERET")
        );
        book.setBookID(rs.getInt("KONYVID"));
        return book;
    }

    // Összes könyv lekérdezése
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM KONYV ORDER BY CIM";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                books.add(getBook(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    // Könyvek lekérdezése DB-ből kulcsszó alapján
    public List<Book> getBooksByKeyword(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM KONYV WHERE LOWER(CIM) LIKE ? OR LOWER(SZERZO) LIKE ? OR LOWER(MUFAJNEV) LIKE ? OR LOWER(ALMUFAJ) LIKE ? ORDER BY CIM";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            stmt.setString(4, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(getBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    // Könyv lekérése DB-ből ID alapján
    public Book findBookByBookID(int BookID) {
        String sql = "SELECT * FROM KONYV WHERE KONYVID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, BookID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = getBook(rs);
                    logger.log(Level.INFO, "Könyv lekérése sikeres");
                    return book;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Konyv lekerese sikertelen: ", e);
        }
        return null;
    }

    // Könyv lekérése DB-ből szerző és cím alapján
    public Book findBookByAuthorAndTitle(String Author, String Title) {
        String sql = "SELECT * FROM KONYV WHERE SZERZO = ? AND CIM = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, Author);
            stmt.setString(2, Title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = getBook(rs);
                    logger.log(Level.INFO, "Könyv lekérése sikeres");
                    return book;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Konyv lekerese sikertelen: ", e);
        }
        return null;
    }

    // Könyv módosítása DB-ben
    public boolean update(Book book) {
        String sql = "UPDATE KONYV SET KIADAS_EVE = ?, KIADO = ?, SZERZO = ?, CIM = ?, MUFAJNEV = ?, ALMUFAJ = ?, OLDALSZAM = ?, DARAB = ?, EKONYV = ?, KOTES = ?, AR = ?, MERET = ? WHERE KONYVID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, book.getPublicationYear());
            stmt.setString(2, book.getPublisher());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getTitle());
            stmt.setString(5, book.getGenre());
            stmt.setString(6, book.getSubGenre());
            stmt.setInt(7, book.getPages());
            stmt.setInt(8, book.getAvailableAmount());
            int ebook = 0;
            if (book.getEbook().equals("Igen")) { ebook = 1;}
            stmt.setInt(9, ebook);
            stmt.setString(10, book.getBinding());
            stmt.setInt(11, book.getPrice());
            stmt.setString(12, book.getSize());
            stmt.setInt(13, book.getBookID());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Konyv modositasa sikertelen: ", e);
            return false;
        }
    }

    // Könyv törlése DB-ből
    public boolean delete(int BookID) {
        String sql = "DELETE FROM KONYV WHERE KONYVID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, BookID);

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Konyv torlese sikertelen: ", e);
            return false;
        }
    }

    // Műfajok lekérdezése DB-ből
    public List<String> getGenresFromDB() {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT DISTINCT MUFAJNEV FROM MUFAJ ORDER BY MUFAJNEV";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                genres.add(rs.getString("MUFAJNEV"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Mufajok lekerdezese sikertelen.", e);
        }
        return genres;
    }

    // Alműfajok lekérdezése DB-ből
    public List<String> getSubGenresFromDB(String genre) {
        List<String> subgenres = new ArrayList<>();
        String sql = "SELECT DISTINCT ALMUFAJ FROM MUFAJ WHERE MUFAJNEV = ? ORDER BY ALMUFAJ";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                subgenres.add(rs.getString("ALMUFAJ"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Almufajok lekerdezese sikertelen.", e);
        }
        return subgenres;
    }

    // Akciós könyvek lekérdezése
    public List<Book> getDiscountedBooks() {
        List<Book> discountedBooks = new ArrayList<>();
        String sql = "SELECT * FROM KONYV WHERE AR <= 2000 ORDER BY AR";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                discountedBooks.add(getBook(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return discountedBooks;
    }

    // Legtöbbször rendelt könyvek (TOP 3) - Összetett lekérdezés
    public List<Book> getBestsellers(){
        List<Book> books = new ArrayList<>();
        String sql = "SELECT k.* FROM KONYV k " +
                "JOIN ( " +
                "    SELECT rf.KONYVID, COUNT(*) AS RENDELES_SZAM " +
                "    FROM RENDELES_F rf " +
                "    JOIN KONYV k2 ON rf.KONYVID = k2.KONYVID " +
                "    GROUP BY rf.KONYVID " +
                "    ORDER BY COUNT(*) DESC " +
                "    FETCH FIRST 3 ROWS ONLY " +
                ") top_books ON k.KONYVID = top_books.KONYVID " +
                "ORDER BY top_books.RENDELES_SZAM DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
            while (rs.next()) {
                books.add(getBook(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
}
