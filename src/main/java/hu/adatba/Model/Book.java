package hu.adatba.Model;

import hu.adatba.Service.GenreService;

import java.sql.SQLException;

public class Book {
    // Adattagok
    private int BookID;
    private final int PublicationYear;
    private final String Publisher;
    private final String Author;
    private final String Title;
    private int GenreID;
    private final int Pages;
    private final String Ebook;
    private final String Binding;
    private final int Price;
    private final String Size;
    private String Discounted;
    private Genre genre;

    // Konstruktor
    public Book(int PublicationYear, String Publisher, String Author, String Title, int GenreID, int Pages, int Ebook, String Binding, int Price, String Size) throws SQLException {
        this.PublicationYear = PublicationYear;
        this.Publisher = Publisher;
        this.Author = Author;
        this.Title = Title;
        this.GenreID = GenreID;
        this.Pages = Pages;
        this.Ebook = Ebook == 1 ? "Igen" : "Nem";
        this.Binding = Binding;
        this.Price = Price;
        this.Size = Size;

        GenreService genreService = new GenreService();
        this.genre = genreService.getGenreByID(GenreID);
    }

    // Getter - Setter
    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public int getPublicationYear() {
        return PublicationYear;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getAuthor() {
        return Author;
    }

    public String getTitle() {
        return Title;
    }

    public int getPages() {
        return Pages;
    }

    public String getEbook() {
        return Ebook;
    }

    public int getGenreID() {
        return GenreID;
    }

    public void setGenreID(int genreID) {
        GenreID = genreID;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getBinding() {
        return Binding;
    }

    public int getPrice() {
        return Price;
    }

    public String getSize() {
        return Size;
    }

    public String getDiscounted() {
        return Discounted;
    }

    public void setDiscounted(int discounted) {
        Discounted = discounted == 1 ? "Igen"  : "Nem";
    }
}
