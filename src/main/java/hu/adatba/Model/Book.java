package hu.adatba.Model;

public class Book {
    // Adattagok
    private int BookID;
    private int PublicationYear;
    private String Publisher;
    private String Author;
    private String Title;
    private String Genre;
    private String SubGenre;
    private int Pages;
    private int AvailableAmount;
    private String Ebook;
    private String Binding;
    private int Price;
    private String Size;

    // Konstruktor
    public Book(int PublicationYear, String Publisher, String Author, String Title, String Genre, String SubGenre, int Pages, int AvailableAmount, int Ebook, String Binding, int Price, String Size) {
        this.PublicationYear = PublicationYear;
        this.Publisher = Publisher;
        this.Author = Author;
        this.Title = Title;
        this.Genre = Genre;
        this.SubGenre = SubGenre;
        this.Pages = Pages;
        this.AvailableAmount = AvailableAmount;
        this.Ebook = Ebook == 1 ? "Igen" : "Nem";
        this.Binding = Binding;
        this.Price = Price;
        this.Size = Size;
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

    public void setPublicationYear(int publicationYear) {
        PublicationYear = publicationYear;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public String getSubGenre() {
        return SubGenre;
    }

    public void setSubGenre(String subGenre) {
        SubGenre = subGenre;
    }

    public int getPages() {
        return Pages;
    }

    public void setPages(int pages) {
        Pages = pages;
    }

    public int getAvailableAmount() {
        return AvailableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        AvailableAmount = availableAmount;
    }

    public String getEbook() {
        return Ebook;
    }

    public void setEbook(int ebook) {
        this.Ebook = ebook == 1 ? "Igen" : "Nem";
    }

    public String getBinding() {
        return Binding;
    }

    public void setBinding(String type) {
        Binding = type;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
}
