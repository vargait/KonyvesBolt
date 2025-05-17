package hu.adatba.DAO;

import hu.adatba.Model.Store;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreDAO {

    private static final Logger logger = Logger.getLogger(StoreDAO.class.getName());

    // Csatlakozás a DB-hez
    private final Connection conn;

    public StoreDAO() throws SQLException {
        this.conn = DBConnect.getConnection();
    }

    private Store getStore(ResultSet rs) throws SQLException{
        Store store = new Store(
                rs.getString("NEV"),
                rs.getString("CIM"),
                rs.getString("TELEFONSZAM"),
                rs.getString("EMAIL")
        );
        store.setStoreID(rs.getInt("ARUHAZID"));
        return store;
    }

    public boolean insertStore(Store store) {
        String sql = "INSERT INTO ARUHAZ (NEV, CIM, TELEFONSZAM, EMAIL) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, store.getStoreName());
            stmt.setString(2, store.getStoreAddress());
            stmt.setString(3, store.getStorePNumber());
            stmt.setString(4, store.getEmail());

            int rowsAdded = stmt.executeUpdate();
            if (rowsAdded > 0) {
                logger.log(Level.INFO, "Aruhaz hozzaadasa DB-hez sikeres.");
                return true;
            } else {
                logger.log(Level.SEVERE, "Aruhaz hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                return false;
            }


        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Aruház hozzáadása a DB-hez sikertelen: ", ex);
        }
        return false;
    }

    public Store findStoreByNameAndAddress(String name, String address){
        String sql = "SELECT * FROM ARUHAZ WHERE NEV = ? AND CIM = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, name);
            stmt.setString(2, address);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Store store = getStore(rs);
                    logger.log(Level.INFO, "Áruház lekérése sikeres");
                    return store;
                }
            }
        }catch(SQLException e){
            logger.log(Level.SEVERE,"Áruház lekérése sikertelen: ",e);
        }
        return null;
    }

    public List<Integer> getStoresFromDB(){
        List<Integer> stores = new ArrayList<>();
        String sql = "SELECT DISTINCT ARUHAZID FROM ARUHAZ ORDER BY ARUHAZID";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                stores.add(rs.getInt("ARUHAZID"));
            }
        }catch(SQLException e){
            logger.log(Level.SEVERE, "Aruhazak lekerdezese sikertelen.", e);
        }
        return stores;
    }

    public List<Store> getAllStores(){
        List<Store> stores = new ArrayList<>();
        String sql = "SELECT * FROM ARUHAZ ORDER BY ARUHAZID";
        try(PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                stores.add(getStore(rs));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return stores;
    }



}
