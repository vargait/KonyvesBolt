package hu.adatba.Model;

public class Invoice {
    private int InvoiceID;
    private int Year;
    private int Price;
    private String Title;
    private String FullName;
    private String Address;

    public Invoice(int InvoiceID, int Year, int Price, String Title, String FullName, String Address) {
        this.InvoiceID = InvoiceID;
        this.Year = Year;
        this.Price = Price;
        this.Title = Title;
        this.FullName = FullName;
        this.Address = Address;
    }

    public int getInvoiceID() {
        return InvoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        InvoiceID = invoiceID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }
}