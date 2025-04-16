package hu.adatba.DAO;

import hu.adatba.Model.User;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

    // Csatlakozás a DB-hez
    private final Connection conn;

    public UserDAO() throws SQLException {
        this.conn = DBConnect.getConnection();
    }

    // Felhasználó hozzáadása DB-hez
    public boolean insertUser(User user) {
        String sql = "INSERT INTO FELHASZNALO (FELHASZNALONEV, EMAIL, JELSZO, TORZSVASARLO, FIZ_ADATOK, ROLE) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.isVIP() ? 1 : 0);
            stmt.setString(5, user.getPaymentMethod());
            stmt.setString(6, user.getRole());

            int rowsAdded = stmt.executeUpdate();
            if (rowsAdded > 0) {
                logger.log(Level.INFO, "Felhasznalo hozzaadasa DB-hez sikeres.");
                return true;
            } else {
                logger.log(Level.SEVERE, "Felhasznalo hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Felhasznalo hozzaadasa DB-hez sikertelen: ", e);
        }
        return false;
    }


    // Felhasználó lekérése DB-ből
    public User findUserByUsername(String username) {
        String sql = "SELECT * FROM FELHASZNALO WHERE FELHASZNALONEV = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                logger.log(Level.INFO, "Felhasznalo lekerese sikeres");
                User user = new User(
                        rs.getString("FELHASZNALONEV"),
                        rs.getString("EMAIL"),
                        rs.getString("JELSZO"),
                        rs.getInt("TORZSVASARLO"),
                        rs.getString("FIZ_ADATOK"),
                        rs.getString("ROLE")
                );
                user.setUserID(rs.getInt("USERID"));
                return user;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Felhasznalo lekerese sikertelen: ", e);
        }
        return null;
    }
}