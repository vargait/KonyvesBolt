package hu.adatba.Service;

import hu.adatba.DAO.OrderDAO;
import hu.adatba.Model.Order;

public class OrderService {
    private final OrderDAO orderDAO;


    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public boolean addOrder(int UserID, int End_Price, String Billing_address, String userName, String card_Number ){
        Order order = new Order(UserID, End_Price, Billing_address, userName, card_Number);
        return orderDAO.insertOrder(order);
    }
}
