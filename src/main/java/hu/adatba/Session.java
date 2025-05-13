package hu.adatba;

import hu.adatba.Model.Book;
import hu.adatba.Model.User;

public class Session {
    private static User loggedInUser;
    private static Book selectedBook;

    public static void setUser(User user) {
        loggedInUser = user;
    }

    public static User getUser() {
        return loggedInUser;
    }

    public static Book getSelectedBook() {
        return selectedBook;
    }

    public static void setSelectedBook(Book book) {
        selectedBook = book;
    }

    public static void clearSelectedBook() {
        selectedBook = null;
    }

    public static void clear() {
        loggedInUser = null;
    }
}
