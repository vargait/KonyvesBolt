package hu.adatba.Service;

import hu.adatba.DAO.CartDAO;
import hu.adatba.Model.Cart;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartService {
    private static final Logger logger = Logger.getLogger(CartService.class.getName());

    private final CartDAO cartDAO;

    public CartService() throws SQLException{
        this.cartDAO = new CartDAO();
    }

    public boolean addCart(int UserID, int letrehozas_ev){
        Cart cart = new Cart(UserID,letrehozas_ev);
        if(cartDAO.findCartByUserID(UserID)== null){
            return cartDAO.createCart(cart);
        }
        logger.log(Level.INFO,"A Kosár már létezik");
        return false;
    }



    public List<Cart> getMyCarts(int UserID){
        return cartDAO.getMyCarts(UserID);
    }
}
