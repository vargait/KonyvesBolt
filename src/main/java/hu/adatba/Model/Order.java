package hu.adatba.Model;

public class Order {
    int OrderID;
    int UserID;
    int Order_Date;
    int End_Price;
    String Billing_address;
    String UserName;
    String Card_Number;



    public Order() {
    }

    public Order(int userID, int order_Date, int end_Price, String billing_address, String userName, String card_Number) {
        UserID = userID;
        Order_Date = order_Date;
        End_Price = end_Price;
        this.Billing_address = billing_address;
        this.UserName = userName;
        this.Card_Number = card_Number;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCard_Number() {
        return Card_Number;
    }

    public void setCard_Number(String card_Number) {
        Card_Number = card_Number;
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

    public int getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(int order_Date) {
        Order_Date = order_Date;
    }

    public int getEnd_Price() {
        return End_Price;
    }

    public void setEnd_Price(int end_Price) {
        End_Price = end_Price;
    }

    public String getBilling_address() {
        return Billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.Billing_address = billing_address;
    }
}


