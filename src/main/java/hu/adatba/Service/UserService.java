package hu.adatba.Service;

import hu.adatba.DAO.UserDAO;
import hu.adatba.Model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    private final UserDAO userDAO;

    public UserService() throws SQLException {
        this.userDAO = new UserDAO();
    }


    // Regisztrálás
    public boolean registerUser(String username, String email, String password, String paymentMethod) {
        User user = new User(username, email, hashPassword(password), 0, paymentMethod, "felhasznalo");
        if(userDAO.findUserByUsername(username) == null) {
            return userDAO.insertUser(user);
        }
        logger.log(Level.INFO, "A felhasznalo mar letezik");
        return false;
    }

    // Bejelentkezés
    public boolean loginUser(String username, String password) {
        User user = userDAO.findUserByUsername(username);

        // Létező felhasználó check
        if (user == null) {
            logger.log(Level.INFO, "Nincs ilyen felhasznalo");
            return false;
        }

        // Jelszó check
        if (verifyPassword(password, user.getPassword())) {
            logger.log(Level.INFO, "Egyeznek a jelszavak");
            return true;
        } else {
            logger.log(Level.INFO, "Nem egyeznek a jelszavak");
            return false;
        }
    }

    // Jelszó titkosítása
    private static String hashPassword(String password) {
        try {
            // Salt
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            // Salt + SHA-512
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);

            // Titkosítás
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashBase64 = Base64.getEncoder().encodeToString(hashedPassword);

            return saltBase64 + ":" + hashBase64;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Nem sikerult a jelszot hashelni", e);
        }
    }


    // Jelszó megerősítése
    public static boolean verifyPassword(String passwordToCheck, String storedPassword){
        try {
            // Eltárolt jelszó szétszedése - Formátum: "Salt:SHA-512"
            String[] parts = storedPassword.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Nem jol titkositott jelszot tarolunk");
            }

            // Salt dekódolása
            byte[] salt = Base64.getDecoder().decode(parts[0]);

            // Megadott jelszó titkosítása
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedInputPassword = md.digest(passwordToCheck.getBytes(StandardCharsets.UTF_8));

            String saltBase64 = Base64.getEncoder().encodeToString(salt);
            String hashedInputBase64 = Base64.getEncoder().encodeToString(hashedInputPassword);

            // Egyezés vizsgálata
            return storedPassword.equals(saltBase64 + ":" + hashedInputBase64);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}