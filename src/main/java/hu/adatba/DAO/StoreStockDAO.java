package hu.adatba.DAO;

import hu.adatba.Model.StoreStock;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreStockDAO {
    private static final Logger logger = Logger.getLogger(StoreStockDAO.class.getName());

    //csatlakozás a dbhez
    private final Connection conn;

    public StoreStockDAO() throws SQLException {
        this.conn = DBConnect.getConnection();
    }

    public boolean insertStoreStock(StoreStock storeStock) {
        String sql = "INSERT INTO ARUHAZKESZLET (ARUHAZID, KONYVID, DARAB_RAKTARON) VALUES (?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storeStock.getShopID());
            stmt.setInt(2, storeStock.getBookID());
            stmt.setInt(3, storeStock.getOnStock());
            int rowsAdded = stmt.executeUpdate();
            if (rowsAdded > 0) {
                logger.log(Level.INFO, "ÁruházStock hozzáadása sikeres.");
                return true;
            } else {
                logger.log(Level.SEVERE, "ÁruházStock hozzáadása sikertelen, 0 sor hozzáadva");
                return false;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "ÁruházStock hozzáadása a DB-hez sikertelen: ", ex);

        }
        return false;

    }
}
