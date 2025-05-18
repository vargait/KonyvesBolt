package hu.adatba.DAO;

import hu.adatba.Model.CartStock;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartStockDAO {
    private static final Logger logger = Logger.getLogger(CartStockDAO.class.getName());


    public CartStock findCartStockByCartIDAndBookID(int CartID, int BookID) {
        String sql = "SELECT * FROM KOSARTETEL WHERE KOSARID = ? AND KONYVID = ?";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, CartID);
                stmt.setInt(2, BookID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        CartStock cartStock = getCartStock(rs);
                        logger.log(Level.INFO,"KosárTétel lekérése sikeres");
                        return cartStock;
                    }
                }
            }
        }catch(SQLException e){
            logger.log(Level.SEVERE, "KosárTétel lekérése sikertelen: ", e);
        }
        return null;
    }
    private CartStock getCartStock(ResultSet rs) throws SQLException{
        CartStock cartStock = new CartStock(
                rs.getInt("KOSARID"),
                rs.getInt("KONYVID"),
                rs.getInt("MENNYISEG"),
                rs.getInt("EGYSEGAR")
        );
        return cartStock;
    }
    public boolean insertCartStock(CartStock cartStock){
        String sql = "INSERT INTO KOSARTETEL (KOSARID, KONYVID, MENNYISEG, EGYSEGAR) VALUES (?, ?, ?, ?)";
        try(Connection conn = DBConnect.getConnection()){
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setInt(1, cartStock.getCartID());
                stmt.setInt(2, cartStock.getBookID());
                stmt.setInt(3, cartStock.getQuantity());
                stmt.setInt(4, cartStock.getPrizeEach());

                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Kosartetel hozzaadasa DB-hez sikeres.");
                    return true;
                } else {
                    logger.log(Level.SEVERE, "Kosartetel hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                    return false;
                }
            }
        } catch(SQLException e){
            logger.log(Level.SEVERE, "Kosartetel hozzaadasa DB-hez sikertelen: ", e);
        }
        return false;
    }

    public List<CartStock> getMyCartStocks(int CartID){
        List<CartStock> cStocks = new ArrayList<>();
        String sql = "SELECT * FROM KOSARTETEL WHERE KOSARID=?";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, CartID);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        cStocks.add(getCartStock(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cStocks;
    }
}
