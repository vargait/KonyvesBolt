package hu.adatba.DAO;

import hu.adatba.Model.Order;
import hu.adatba.db.DBConnect;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    private static final Logger logger = Logger.getLogger(OrderDAO.class.getName());

    public Order getOrder(ResultSet rs) throws SQLException {
        return new Order(
                rs.getInt("UserID"),
                rs.getInt("Order_Date"),
                rs.getInt("End_Price"),
                rs.getString("Billing_address"),
                rs.getString("UserName"),
                rs.getString("Card_Number")
        );
    }
        public void callCopyCartToOrder(int orderID) {
            try (Connection conn = DBConnect.getConnection()) {

                assert conn != null;
                CallableStatement stmt = conn.prepareCall("{call CopyKosarToRendeles(?)}");
                stmt.setInt(1, orderID);
                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


            public boolean insertOrder(Order order){
        String sql = "INSERT INTO RENDELES (USERID, VEGOSSZEG, SZAMLAZASI_CIM, FELHASZNALONEV, KARTYASZAM) VALUES(?,?,?,?,?)";
        try(Connection conn = DBConnect.getConnection()){
            assert conn!= null;
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, order.getUserID());
                stmt.setInt(2, order.getEnd_Price());
                stmt.setString(3, order.getBilling_address());
                stmt.setString(4, order.getUserName());
                stmt.setString(5, order.getCard_Number());

                int rowsAdded = stmt.executeUpdate();
                if(rowsAdded > 0){
                    logger.log(Level.INFO,"Sikeres rendeles!");
                    return true;
                }else{
                    logger.log(Level.SEVERE, "Sikertelen rendeles!");
                    return false;
                }

            }
        }catch(SQLException e){
            logger.log(Level.SEVERE,"Rendeles hozzaadasa a DB-hez sikertelen: ",e);
        }
        return false;
    }
}
