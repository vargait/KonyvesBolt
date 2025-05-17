package hu.adatba.Service;

import hu.adatba.DAO.StoreDAO;
import hu.adatba.Model.Store;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreService {
    private static final Logger logger = Logger.getLogger(StoreService.class.getName());

    private final StoreDAO storeDAO;

    public StoreService() throws SQLException{
        this.storeDAO = new StoreDAO();
    }

    public boolean addStore(String storeName, String storeAddress, String storePNumber, String email){
        Store store = new Store(storeName, storeAddress, storePNumber, email);
        if(storeDAO.findStoreByNameAndAddress(storeName, storeAddress) == null){
            return storeDAO.insertStore(store);
        }
        logger.log(Level.INFO, "Az aruhaz mar letezik");
        return false;
    }

}
