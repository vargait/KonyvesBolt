package hu.adatba.Service;

import hu.adatba.DAO.OrderDAO;
import hu.adatba.Model.Order;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderService {
    private static final Logger logger = Logger.getLogger(OrderService.class.getName());
    private final OrderDAO orderDAO;

    public OrderService() throws SQLException {
        this.orderDAO = new OrderDAO();
    }

    public boolean addOrder(Order order) {
        if(orderDAO.insertOrder(order)) {
            logger.log(Level.INFO, "Megrendelés sikeresen leadva.");
            return true;
        }
        logger.log(Level.SEVERE, "Megrendelés sikertelen.");
        return false;
    }
}
