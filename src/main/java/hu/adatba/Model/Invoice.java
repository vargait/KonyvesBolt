package hu.adatba.Model;

import hu.adatba.Service.BookService;
import hu.adatba.Service.OrderService;

public class Invoice {
    private int OrderID;
    private String FullName;
    private int Date;
    private String Address;
    private int CreditNumber;
    private String BookCount;
    private int TotalPrice;

    public Invoice(int OrderID, String FullName, int Date, String Address, int CreditNumber, String BookCount, int TotalPrice) {
        this.OrderID = OrderID;
        this.FullName = FullName;
        this.Date = Date;
        this.Address = Address;
        this.CreditNumber = CreditNumber;
        this.BookCount = BookCount;
        this.TotalPrice = TotalPrice;
    }

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

    public String getBookCount() {
        return BookCount;
    }

    public void setBookCount(String bookCount) {
        BookCount = bookCount;
    }

    public int getCreditNumber() {
        return CreditNumber;
    }

    public void setCreditNumber(int creditNumber) {
        CreditNumber = creditNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getDate() {
        return Date;
    }

    public void setDate(int date) {
        Date = date;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}