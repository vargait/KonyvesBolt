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

    // Könyv hozzáadása DB-hez
    public boolean insertBook(Book book) {
        String sql = "INSERT INTO KONYV (CIM, KIADAS_EVE, OLDALSZAM, KOTES, AR, SZERZO, MERET, EKONYV, KIADO, MUFAJID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setInt(2, book.getPublicationYear());
                stmt.setInt(3, book.getPages());
                stmt.setString(4, book.getBinding());
                stmt.setInt(5, book.getPrice());
                stmt.setString(6, book.getAuthor());
                stmt.setString(7, book.getSize());
                int ebook = 0;
                if (book.getEbook().equals("Igen")) { ebook = 1;}
                stmt.setInt(8, ebook);
                stmt.setString(9, book.getPublisher());
                stmt.setInt(10, book.getGenreID());


                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Konyv hozzaadasa DB-hez sikeres.");
                    return true;
                } else {
                    logger.log(Level.SEVERE, "Konyv hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                    return false;
                }
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
                rs.getInt("MUFAJID"),
                rs.getInt("OLDALSZAM"),
                rs.getInt("EKONYV"),
                rs.getString("KOTES"),
                rs.getInt("AR"),
                rs.getString("MERET")
        );
        book.setBookID(rs.getInt("KONYVID"));
        book.setDiscounted(rs.getInt("AKCIOS_E"));
        return book;
    }

    // Összes könyv lekérdezése
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM KONYV ORDER BY CIM";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    books.add(getBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    // Könyvek lekérdezése DB-ből kulcsszó alapján
    public List<Book> getBooksByKeyword(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM KONYV WHERE LOWER(CIM) LIKE ? OR LOWER(SZERZO) LIKE ? ORDER BY CIM";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + keyword + "%");
                stmt.setString(2, "%" + keyword + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        books.add(getBook(rs));
                    }
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
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, BookID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Book book = getBook(rs);
                        logger.log(Level.INFO, "Könyv lekérése sikeres");
                        return book;
                    }
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
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
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
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Konyv lekerese sikertelen: ", e);
        }
        return null;
    }

    // Könyv módosítása DB-ben
    public boolean update(Book book) {
        String sql = "UPDATE KONYV SET KIADAS_EVE = ?, KIADO = ?, SZERZO = ?, CIM = ?, MUFAJID = ?, OLDALSZAM = ?, EKONYV = ?, KOTES = ?, AR = ?, MERET = ? WHERE KONYVID = ?";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, book.getPublicationYear());
                stmt.setString(2, book.getPublisher());
                stmt.setString(3, book.getAuthor());
                stmt.setString(4, book.getTitle());
                stmt.setInt(5, book.getGenreID());
                stmt.setInt(6, book.getPages());
                int ebook = 0;
                if (book.getEbook().equals("Igen")) { ebook = 1;}
                stmt.setInt(7, ebook);
                stmt.setString(8, book.getBinding());
                stmt.setInt(9, book.getPrice());
                stmt.setString(10, book.getSize());
                stmt.setInt(11, book.getBookID());

                int rows = stmt.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Konyv modositasa sikertelen: ", e);
            return false;
        }
    }

    // Könyv törlése DB-ből
    public boolean delete(int BookID) {
        String sql = "DELETE FROM KONYV WHERE KONYVID = ?";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, BookID);

                int rows = stmt.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Konyv torlese sikertelen: ", e);
            return false;
        }
    }

    // Akciós könyvek lekérdezése
    public List<Book> getDiscountedBooks() {
        List<Book> discountedBooks = new ArrayList<>();
        String sql = "SELECT * FROM KONYV WHERE AKCIOS_E = 1 ORDER BY AR";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    discountedBooks.add(getBook(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return discountedBooks;
    }

    //Könyv ID-jének lekérése az ÁruházKészlet hozzáadáshoz
    public List<Integer> getBooksFromDB(){
        List<Integer> books = new ArrayList<>();
        String sql = "SELECT KONYVID FROM KONYV ORDER BY KONYVID";
        try(Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    books.add(rs.getInt("KONYVID"));
                }
            }
        } catch (SQLException err){
            logger.log(Level.SEVERE, "Könyvek lekérdezése sikertelen.", err);
        }
        return books;
    }
}
