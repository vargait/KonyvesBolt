package hu.adatba.DAO;

import hu.adatba.Model.Book;
import hu.adatba.Model.Cart;
import hu.adatba.Model.User;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartDAO {
    private static final Logger logger = Logger.getLogger(CartDAO.class.getName());



    public List<Cart> getAllCarts() {
        List<Cart> carts = new ArrayList<>();
        String sql = "SELECT * FROM KOSAR ORDER BY KOSARID";

        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        carts.add(getCart(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return carts;
    }


        public Cart getCart(ResultSet rs) throws SQLException{
            Cart cart = new Cart(
                    rs.getInt("USERID"),
                    rs.getInt("LETREHOZAS_DATUMA")
            );
            cart.setKosarID(rs.getInt("KOSARID"));
            return cart;
        }

    public Cart findCartByUserID(int UserID) {
        String sql = "SELECT * FROM KOSAR WHERE USERID = ?";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, UserID);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Cart cart = getCart(rs);
                        logger.log(Level.INFO, "Kosár lekérése sikeres");
                        return cart;
                    }
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Kosár lekérése sikertelen");
        }
        return null;
    }


    public boolean createCart(Cart cart) {
        String sql = "INSERT INTO KOSAR (USERID, LETREHOZAS_DATUMA) VALUES(?, ?)";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, cart.getUserID());
                stmt.setInt(2, cart.getLetrehozas_ev());

                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Kosar hozzaadasa DB-hez sikeres.");
                    return true;
                } else {
                    logger.log(Level.SEVERE, "Kosar hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                    return false;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Kosar hozzaadasa a DBhez sikertelen: e");

        }
        return false;
    }

    public boolean insertBook(int CartID, int BookID, int Amount, int Price) throws SQLException {
        String sql = "INSERT INTO KOSARTETEL VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnect.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, CartID);
                stmt.setInt(2, BookID);
                stmt.setInt(3, Amount);
                stmt.setInt(4, Price);

                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Konyv hozzaadasa kosarhoz sikeres");
                    return true;
                } else{
                    logger.log(Level.SEVERE, ("Konyv hozzaadasa kosarhoz sikertelen"));
                    return false;
                }
            }
        }
    }

    public boolean deleteCartByUserID(int userID) {
        String sql = "DELETE FROM KOSAR WHERE USERID = ?";
        try (Connection conn = DBConnect.getConnection()){
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                int rowsDeleted = stmt.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


