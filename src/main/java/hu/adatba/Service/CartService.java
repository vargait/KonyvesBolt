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

    public CartService() {
        this.cartDAO = new CartDAO();
    }
    public void addGuestCart(){
        if(cartDAO.createGuestCart()){
            logger.log(Level.INFO,"VendégKosár létrejött");
        }
        else{
            logger.log(Level.SEVERE,"Vendégkosár már létezik/nem tudott létrejönni");
        }
    }

    public void addCart(int UserID){
        Cart cart = new Cart(UserID);
        if(cartDAO.findCartByUserID(UserID)== null){
            cartDAO.createCart(cart);
            return;
        }
        logger.log(Level.INFO,"A Kosár már létezik");
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
