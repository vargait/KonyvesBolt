package hu.adatba.DAO;

import hu.adatba.Model.StoreStock;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreStockDAO {
    private static final Logger logger = Logger.getLogger(StoreStockDAO.class.getName());

    private StoreStock getStoreStock(ResultSet rs) throws SQLException{
        StoreStock storeStock = new StoreStock(
                rs.getInt("ARUHAZID"),
                rs.getInt("KONYVID"),
                rs.getInt("DARAB_RAKTARON")
        );
        return storeStock;
    }

    public StoreStock findStoreStockByBookIDAndStoreID(int StoreID, int BookID){
        String sql = "SELECT * FROM ARUHAZKESZLET WHERE ARUHAZID = ? AND KONYVID = ?";
        try(Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, StoreID);
                stmt.setInt(2, BookID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        StoreStock storeStock = getStoreStock(rs);
                        logger.log(Level.INFO, "AruházKészlet lekérése sikeres");
                        return storeStock;
                    }
                }

            }
        } catch(SQLException e){
            logger.log(Level.SEVERE, "ÁruházKészlet lekérése sikertelen: ",e);
        }
        return null;
    }

    public boolean insertStoreStock(StoreStock storeStock) {
        String sql = "INSERT INTO ARUHAZKESZLET (ARUHAZID, KONYVID, DARAB_RAKTARON) VALUES (?,?,?)";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, storeStock.getShopID());
                stmt.setInt(2, storeStock.getBookID());
                stmt.setInt(3, storeStock.getOnStock());
                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Aruhazkeszlet hozzáadása sikeres.");
                    return true;
                } else {
                    logger.log(Level.SEVERE, "Aruhazkeszlet hozzáadása sikertelen, 0 sor hozzáadva");
                    return false;
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Aruhazkeszlet hozzáadása a DB-hez sikertelen: ", ex);

        }
        return false;

    }

    public List<StoreStock> getAllStoreStocks(){
        List<StoreStock> storeStocks = new ArrayList<>();
        String sql = "SELECT * FROM ARUHAZKESZLET ORDER BY ARUHAZID";
        try(Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try(PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    storeStocks.add(getStoreStock(rs));
                }
            }
        } catch(SQLException err){
            throw new RuntimeException(err);
        }
        return storeStocks;
    }

    public boolean updateStock(int newStock, int storeID, int bookID) {
        String sql = "UPDATE ARUHAZKESZLET " +
                "SET DARAB_RAKTARON = ? " +
                "WHERE ARUHAZID = ? AND KONYVID = ?";
        try (Connection conn = DBConnect.getConnection()){
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, newStock);
                stmt.setInt(2, storeID);
                stmt.setInt(3, bookID);

                int rowsUpdated = stmt.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
