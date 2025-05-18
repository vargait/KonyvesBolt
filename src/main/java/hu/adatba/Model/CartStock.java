package hu.adatba.Model;

public class CartStock {
    private int CartID;
    private int BookID;
    private int Quantity;
    private int PrizeEach;

    CartStock(){}

    public CartStock(int cartID, int bookID, int quantity, int prizeEach) {
        CartID = cartID;
        BookID = bookID;
        Quantity = quantity;
        PrizeEach = prizeEach;
    }

    public int getCartID() {
        return CartID;
    }

    public void setCartID(int cartID) {
        CartID = cartID;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getPrizeEach() {
        return PrizeEach;
    }

    public void setPrizeEach(int prizeEach) {
        PrizeEach = prizeEach;
    }
}
