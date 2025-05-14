package hu.adatba.Model;

public class Order {
    private int OrderID;
    private int UserID;
    private int BookID;
    private String Address;
    private String CreditNumber;
    private String Fullname;
    private String ipAddress;
    private String BookTitle;
    private String Discounted;

    // Bejelentkezett rendelés
    public Order(int UserID, int BookID) {
        this.UserID = UserID;
        this.BookID = BookID;
    }

    // Látogató rendelés
    public Order(String ipAddress, int BookID, String Address, String CreditNumber, String Fullname) {
        this.ipAddress = ipAddress;
        this.BookID = BookID;
        this.Address = Address;
        this.CreditNumber = CreditNumber;
        this.Fullname = Fullname;
    }

    public Order(int OrderID, int Discounted, String Fullname, String BookTitle){
        this.OrderID = OrderID;
        this.Fullname = Fullname;
        this.Discounted = Discounted == 1 ? "Igen" : "Nem";
        this.BookTitle = BookTitle;
    }

    // Getter - Setter


    public String getDiscounted() {
        return Discounted;
    }

    public void setDiscounted(String discounted) {
        Discounted = discounted;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getCreditNumber() {
        return CreditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        CreditNumber = creditNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getBookID() {
        return BookID;
    }

    public void setBookID(int bookID) {
        BookID = bookID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
