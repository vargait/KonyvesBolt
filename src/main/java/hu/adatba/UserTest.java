package hu.adatba;

import hu.adatba.Service.UserService;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserTest {
    private static final Logger logger = Logger.getLogger(UserTest.class.getName());

    public static void main(String[] args) throws SQLException {
        UserService userService = new UserService();

        // TODO: Controller készítése, ha lesz GUI

        // Példa input
        String username = "ProbaUser3";
        String password = "1434";
        String email = "proba2@user.com";
        String paymentMethod = "Kártya";


        // Regisztráció teszt
        boolean success = userService.registerUser(username, email, password, paymentMethod);
        if (success) {
            logger.log(Level.INFO, "Felhasznalo sikeresen regisztralt");
        } else {
            logger.log(Level.INFO, "Felhasznalo regisztralasa sikertelen");
        }

        // Bejelentkezés teszt
        boolean loginSuccess = userService.loginUser(username, password);
        if (loginSuccess) {
            logger.log(Level.INFO, "Sikeres bejelentkezes");
        } else {
            logger.log(Level.INFO, "Sikertelen bejelentkezes");
        }
    }
}
