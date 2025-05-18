package hu.adatba.Service;

import hu.adatba.DAO.CartStockDAO;
import hu.adatba.Model.CartStock;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartStockService {

    private static final Logger logger = Logger.getLogger(CartStockService.class.getName());

    private final CartStockDAO cartStockDAO;

    public CartStockService() throws SQLException{
        this.cartStockDAO = new CartStockDAO();
    }

    public List<CartStock> getMyCartStocks(int CartID){
        return cartStockDAO.getMyCartStocks(CartID);
    }
    public boolean addCartStock(int CartID, int BookID, int Quantity, int PrizeEach){
        CartStock cartStock = new CartStock(CartID, BookID, Quantity, PrizeEach);
        if(cartStockDAO.findCartStockByCartIDAndBookID(CartID, BookID)== null){
            return cartStockDAO.insertCartStock(cartStock);
        }
        logger.log(Level.INFO, "A könyvből már van tételed a kosárban!");
        return false;
    }
    public CartStock findCartStockByCartIDAndBookID(int CartID, int BookID){return cartStockDAO.findCartStockByCartIDAndBookID(CartID, BookID);}
}
