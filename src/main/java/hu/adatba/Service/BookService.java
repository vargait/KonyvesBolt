package hu.adatba.Service;

import hu.adatba.DAO.BookDAO;
import hu.adatba.Model.Book;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookService {
    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    private final BookDAO bookDAO;

    public BookService() throws SQLException {
        this.bookDAO = new BookDAO();
    }

    // Könyv hozzáadása
    public boolean addBook(int PublicationYear, String Publisher, String Author, String Title, String Genre, String SubGenre, int Pages, int AvailableAmount, int Ebook, String Binding, int Price, String Size) throws SQLException {
        Book book = new Book(PublicationYear, Publisher, Author, Title, Genre, SubGenre, Pages, AvailableAmount, Ebook, Binding, Price, Size);
        if(bookDAO.findBookByAuthorAndTitle(Author, Title) == null) {
            return bookDAO.insertBook(book);
        }
        logger.log(Level.INFO, "A konyv mar letezik");
        return false;
    }

    // Könyv törlése
    public boolean deleteBook(int BookID) throws SQLException {
        if(bookDAO.findBookByBookID(BookID) != null) {
            return bookDAO.delete(BookID);
        }
        logger.log(Level.INFO, "A konyv nem letezik");
        return false;
    }

    // Könyv módosítása
    public boolean updateBook(Book book) {
        return bookDAO.update(book);
    }

    // Összes könyv lekérdezése
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    // Műfaj lekérdezése
    public List<String> getGenres(){
        return bookDAO.getGenresFromDB();
    }

    // Alműfaj lekérdezése
    public List<String> getSubGenres(String genre){
        return bookDAO.getSubGenresFromDB(genre);
    }
}
