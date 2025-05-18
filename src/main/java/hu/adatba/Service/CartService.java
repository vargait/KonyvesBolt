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



    public List<Cart> getAllCarts(){
        return cartDAO.getAllCarts();
    }

    public boolean insertBook(int CartID, int UserID, int Amount, int Price) throws SQLException {
        return cartDAO.insertBook(CartID, UserID, Amount, Price);
    }

    public Cart findCartByUserID(int UserID){
        return cartDAO.findCartByUserID(UserID);
    }

    public boolean deleteCartByUserID(int UserID){
        return cartDAO.deleteCartByUserID(UserID);
    }
}
