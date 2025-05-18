package hu.adatba.Service;

import hu.adatba.DAO.StoreStockDAO;
import hu.adatba.Model.StoreStock;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreStockService {
    private static final Logger logger = Logger.getLogger(StoreStockService.class.getName());

    private final StoreStockDAO storeStockDAO;

    public StoreStockService() {
        this.storeStockDAO = new StoreStockDAO();
    }

    public Boolean addStoreStock(int StoreID, int BookID, int Quantity){
        StoreStock storeStock = new StoreStock(StoreID, BookID, Quantity);
        if(storeStockDAO.findStoreStockByBookIDAndStoreID(StoreID, BookID)== null){
            return storeStockDAO.insertStoreStock(storeStock);
        }
        logger.log(Level.INFO, "A keszlet mar letezik");
        return false;
    }
    public List<StoreStock> getStoreStocks(){
        return storeStockDAO.getAllStoreStocks();
    }

    public StoreStock getStockByStoreAndBook(int StoreID, int BookID) throws SQLException {
        return storeStockDAO.findStoreStockByBookIDAndStoreID(StoreID, BookID);
    }

    public boolean updateStock(int newStock, int StoreID, int BookID) {
        return storeStockDAO.updateStock(newStock, StoreID, BookID);
    }
}
