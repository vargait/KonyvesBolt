package hu.adatba.DAO;

import hu.adatba.Model.Cart;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartDAO {
    private static final Logger logger = Logger.getLogger(CartDAO.class.getName());

    public Cart getCart(ResultSet rs) throws SQLException {
        Cart cart = new Cart(
                rs.getInt("KOSARID"),
                rs.getInt("USERID"),
                rs.getInt("LETREHOZAS_EV")
        );
        return cart;
    }

    public boolean createCart(Cart cart){
        String sql = "INSERT INTO KOSAR (USERID, LETREHOZAS_EV) VALUES(?, ?, ?)";
        try(Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, cart.getUserID());
                stmt.setInt(2, cart.getLetrehozas_ev());

                int rowsAdded= stmt.executeUpdate();
                if(rowsAdded > 0){
                    logger.log(Level.INFO, "Kosar hozzaadasa DB-hez sikeres.");
                    return true;
                }else{
                    logger.log(Level.SEVERE, "Kosar hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                    return false;
                }
            }
        } catch(SQLException e){
            logger.log(Level.SEVERE, "Kosar hozzaadasa a DBhez sikertelen: e");

        }
        return false;
    }



}
