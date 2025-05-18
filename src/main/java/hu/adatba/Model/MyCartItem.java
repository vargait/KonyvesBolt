package hu.adatba.Model;

import hu.adatba.Service.BookService;
import hu.adatba.Service.CartService;
import hu.adatba.Service.CartStockService;

import java.sql.SQLException;

public class MyCartItem {
    private Book book;
    private CartStock cartStock;

    public MyCartItem(int BookID, int CartID) throws SQLException {
        BookService bookService = new BookService();
        CartStockService cartStockService = new CartStockService();

        this.book = bookService.getBookByID(BookID);
        this.cartStock = cartStockService.findCartStockByCartIDAndBookID(CartID, BookID);
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public CartStock getCartStock() {
        return cartStock;
    }

    public void setCartStock(CartStock cartStock) {
        this.cartStock = cartStock;
    }

    public int getTotalPrice() {
        return this.book.getPrice() * this.cartStock.getQuantity();
    }
}
