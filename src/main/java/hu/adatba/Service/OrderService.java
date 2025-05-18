package hu.adatba.Service;

import hu.adatba.DAO.OrderDAO;
import hu.adatba.Model.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderService {


    private static final Logger logger = Logger.getLogger(OrderService.class.getName());
    private final OrderDAO orderDAO;


    public OrderService() throws SQLException {
        this.orderDAO = new OrderDAO();
    }

    public boolean addOrder(int UserID, int Order_Date, int End_Price, String Billing_address, String userName, String card_Number ){
        Order order = new Order(UserID, Order_Date, End_Price, Billing_address, userName, card_Number);
        orderDAO.callCopyCartToOrder(UserID);
        return orderDAO.insertOrder(order);
    }

    /*



    public boolean addOrder(Order order) {
        if(orderDAO.insertOrder(order)) {
            logger.log(Level.INFO, "Megrendelés sikeresen leadva.");
            return true;
        }
        logger.log(Level.SEVERE, "Megrendelés sikertelen.");
        return false;
    }
    public List<Order> getAllOrders(String type){
        return orderDAO.getAllBooks(type);
    }*/
}
