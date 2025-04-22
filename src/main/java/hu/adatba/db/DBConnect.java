package hu.adatba.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConnect {
    private static final Logger logger = Logger.getLogger(DBConnect.class.getName());
    static Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        if(URL != null){
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        logger.log(Level.SEVERE, "Nem jött létre a kapcsolat a DB-vel!");
        return null;
    }
}