package hu.adatba.DAO;

import hu.adatba.Model.Book;
import hu.adatba.Model.Order;
import hu.adatba.Model.QueryResult;
import hu.adatba.Session;
import hu.adatba.db.DBConnect;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    private static final Logger logger = Logger.getLogger(OrderDAO.class.getName());
    // Csatlakozás a DB-hez
    private final Connection conn;

    public OrderDAO() throws SQLException {
        this.conn = DBConnect.getConnection();
    }

    // Rendelés hozzáadása DB-hez
    public boolean insertOrder(Order order) {
        String sql;
        if (Session.getUser().getRole().equals("latogato")) {
            sql = "INSERT INTO RENDELES_L (IP_CIM, KONYVID, FELH_CIM, FELH_KARTYA, FELH_NEV) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, order.getIpAddress());
                stmt.setInt(2, order.getBookID());
                stmt.setString(3, order.getAddress());
                stmt.setString(4, order.getCreditNumber());
                stmt.setString(5, order.getFullname());

                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Rendeles hozzaadasa DB-hez sikeres.");
                    return true;
                } else {
                    logger.log(Level.SEVERE, "Rendeles hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                    return false;
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Rendeles hozzaadasa DB-hez sikertelen: ", e);
            }
        } else {
            sql = "INSERT INTO RENDELES_F (USERID, KONYVID) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, order.getUserID());
                stmt.setInt(2, order.getBookID());

                int rowsAdded = stmt.executeUpdate();
                if (rowsAdded > 0) {
                    logger.log(Level.INFO, "Rendeles hozzaadasa DB-hez sikeres.");
                    return true;
                } else {
                    logger.log(Level.SEVERE, "Rendeles hozzaadasa DB-hez sikertelen: lefutott, 0 sor hozzaadva");
                    return false;
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Rendeles hozzaadasa DB-hez sikertelen: ", e);
            }
        }
        return false;
    }

    // ÖSSZETETT LEKÉRDEZÉSEK

    // +5 féle összetett lekérdezés megvalósítása statisztika jelleggel - admin
    public List<QueryResult> getQueryResults(int queryIndex) {
        String sql = "";
        switch (queryIndex) {
            case 1:
                sql = "SELECT f.FELHASZNALONEV, COUNT(r.RENDELESFID) AS RENDELES_DB " +
                        "FROM FELHASZNALO f " +
                        "JOIN RENDELES_F r ON f.USERID = r.USERID " +
                        "WHERE f.TORZSVASARLO = 1 " +
                        "GROUP BY f.FELHASZNALONEV";
                break;

            case 2:
                sql = "SELECT f.FELHASZNALONEV, SUM(sz.AR) AS OSSZ_KOLTSEG " +
                        "FROM FELHASZNALO f " +
                        "JOIN RENDELES_F r ON f.USERID = r.USERID " +
                        "JOIN SZAMLA_F sz ON sz.RENDELESFID = r.RENDELESFID " +
                        "GROUP BY f.FELHASZNALONEV " +
                        "ORDER BY OSSZ_KOLTSEG DESC";
                break;

            case 3:
                sql = "SELECT f.FELHASZNALONEV, k.CIM " +
                        "FROM RENDELES_F r " +
                        "JOIN KONYV k ON r.KONYVID = k.KONYVID " +
                        "JOIN FELHASZNALO f ON r.RENDELESFID = f.USERID " +
                        "WHERE r.AKCIO_E = 1";
                break;

            case 4:
                sql = "SELECT sz.DATUM_EV, COUNT(*) AS KONYV_ELADAS_DB " +
                        "FROM SZAMLA_F sz " +
                        "GROUP BY sz.DATUM_EV " +
                        "ORDER BY sz.DATUM_EV";
                break;

            case 5:
                sql = "SELECT k.MUFAJNEV, COUNT(r.RENDELESFID) AS ELADOTT_DB " +
                        "FROM KONYV k " +
                        "JOIN RENDELES_F r ON k.KONYVID = r.KONYVID " +
                        "GROUP BY k.MUFAJNEV " +
                        "ORDER BY ELADOTT_DB DESC";
                break;
        }


        List<QueryResult> result = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                QueryResult qr = new QueryResult(
                        rs.getString(1),
                        rs.getString(2)
                );
                result.add(qr);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public Order getOrder(ResultSet rs) throws SQLException {
        Order order = new Order(
                rs.getInt("USERID"),
                rs.getInt("KONYVID")
        );
        order.setOrderID(rs.getInt("RENDELESFID"));
        return order;
    }

    // Összes könyv lekérdezése
    public List<Order> getAllBooks() {
        int uid = Session.getUser().getUserID();
        if (Session.getUser().getRole().equals("admin")) {
            List<Order> orders = new ArrayList<>();
            String sql = "SELECT * FROM RENDELES_F ORDER BY RENDELESFID";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    orders.add(getOrder(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return orders;

        } else {


            List<Order> orders = new ArrayList<>();
            String sql = "SELECT * FROM RENDELES_F WHERE USERID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, uid);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    orders.add(getOrder(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return orders;
        }

    }
}
