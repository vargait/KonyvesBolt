package hu.adatba.Model;

public class Order {
    private int OrderID;
    private int UserID;
    private int OrderDate;
    private int TotalPrice;
    private String orderAddress;

    // Konstruktor beszúráshoz
    public Order(int UserID, int TotalPrice, String orderAddress) {
        this.UserID = UserID;
        this.TotalPrice = TotalPrice;
        this.orderAddress = orderAddress;
    }

    // Konstruktor lekérdezéshez
    public Order(int OrderID, int UserID, int OrderDate, int TotalPrice, String orderAddress) {
        this.OrderID = OrderID;
        this.UserID = UserID;
        this.OrderDate = OrderDate;
        this.TotalPrice = TotalPrice;
        this.orderAddress = orderAddress;
    }

    // Getter - Setter

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        TotalPrice = totalPrice;
    }

    public int getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(int orderDate) {
        OrderDate = orderDate;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }
}
