package hu.adatba.DAO;

import hu.adatba.Model.Order;
import hu.adatba.Session;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    private static final Logger logger = Logger.getLogger(OrderDAO.class.getName());

    // Csatlakozás a DB-hez
    private final Connection conn;

    public OrderDAO() throws SQLException {
        this.conn = DBConnect.getConnection();
    }

    // Rendelés hozzáadása DB-hez
    public boolean insertOrder(Order order) {
        String sql;
        if(Session.getUser().getRole().equals("latogato")) {
            sql = "INSERT INTO RENDELES_L (IP_CIM, KONYVID, FELH_CIM, FELH_KARTYA, FELH_NEV) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, order.getIpAddress());
                stmt.setInt(2, order.getBookID());
                stmt.setString(3, order.getAddress());
                stmt.setString(4, order.getCreditNumber());
                stmt.setString(5, order.getFullname());

                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Rendeles hozzaadasa DB-hez sikeres.");
                    return true;
                } else {
                    logger.log(Level.SEVERE, "Rendeles hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                    return false;
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Rendeles hozzaadasa DB-hez sikertelen: ", e);
            }
        }
        else{
            sql = "INSERT INTO RENDELES_F (USERID, KONYVID) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, order.getUserID());
                stmt.setInt(2, order.getBookID());

                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Rendeles hozzaadasa DB-hez sikeres.");
                    return true;
                } else {
                    logger.log(Level.SEVERE, "Rendeles hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                    return false;
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Rendeles hozzaadasa DB-hez sikertelen: ", e);
            }
        }
        return false;
    }
}
