package hu.adatba.Model;

public class StoreStock {
    private int ShopID;
    private int BookID;
    private int onStock;

    public StoreStock(){}

    public StoreStock(int shopID, int bookID, int onStock) {
        ShopID = shopID;
        BookID = bookID;
        this.onStock = onStock;
    }

    public int getShopID() {
        return ShopID;
    }

    public void setShopID(int shopID) {
        ShopID = shopID;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public int getOnStock() {
        return onStock;
    }

    public void setOnStock(int onStock) {
        this.onStock = onStock;
    }
}
