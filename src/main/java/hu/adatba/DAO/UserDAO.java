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
        String sql = "INSERT INTO FELHASZNALO (FELHASZNALONEV, EMAIL, JELSZO, TELJES_NEV, SZALLITASI_CIM, KARTYASZAM, TORZSVASARLO, ROLE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getPostalAddress());
            stmt.setString(6, user.getCreditNumber());
            stmt.setInt(7, user.isVIP() ? 1 : 0);
            stmt.setString(8, "felhasznalo");

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
                        rs.getString("TELJES_NEV"),
                        rs.getString("SZALLITASI_CIM"),
                        rs.getString("KARTYASZAM")
                );
                user.setUserID(rs.getInt("USERID"));
                user.setRole(rs.getString("ROLE"));
                return user;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Felhasznalo lekerese sikertelen: ", e);
        }
        return null;
    }

    // Felhasználó módosítása DB-ben
    public boolean update(User user) {
        String sql = "UPDATE FELHASZNALO SET FELHASZNALONEV = ?, EMAIL = ?, JELSZO = ?, TELJES_NEV = ?, SZALLITASI_CIM = ?, KARTYASZAM = ? WHERE USERID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getFullName());
            stmt.setString(5, user.getPostalAddress());
            stmt.setString(6, user.getCreditNumber());
            stmt.setInt(7, user.getUserID());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Felhasznalo modositasa sikertelen: ", e);
            return false;
        }
    }
}