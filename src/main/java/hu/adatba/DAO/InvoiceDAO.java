package hu.adatba.DAO;

import hu.adatba.Model.Invoice;
import hu.adatba.Session;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    // Csatlakozás a DB-hez
    private final Connection conn;

    public InvoiceDAO() throws SQLException {
        this.conn = DBConnect.getConnection();
    }/*

    public Invoice getInvoice(ResultSet rs) throws SQLException {
        return new Invoice(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
        );
    }

    // Összes számla lekérdezése - Összetett lekérdezések
    public List<Invoice> getAllInvoices(String type) {
        int uid = Session.getUser().getUserID();
        List<Invoice> invoices = new ArrayList<>();
        if (Session.getUser().getRole().equals("admin")) {
            String sql;
            if(type.equals("felhasznalo")){
                sql = "SELECT SZAMLA_F.SZAMLAFID, SZAMLA_F.DATUM_EV, SZAMLA_F.AR, " +
                        "K.CIM, F.TELJES_NEV, F.FELH_CIM " +
                        "FROM SZAMLA_F " +
                        "JOIN RENDELES_F RF ON SZAMLA_F.RENDELESFID = RF.RENDELESFID " +
                        "JOIN KONYV K ON RF.KONYVID = K.KONYVID " +
                        "JOIN FELHASZNALO F ON RF.USERID = F.USERID " +
                        "ORDER BY SZAMLA_F.SZAMLAFID";
            }
            else{
                sql = "SELECT SZAMLA_L.SZAMLALID, SZAMLA_L.DATUM_EV, SZAMLA_L.AR, " +
                        "K.CIM, RL.FELH_NEV, RL.FELH_CIM " +
                        "FROM SZAMLA_L " +
                        "JOIN RENDELES_L RL ON SZAMLA_L.RENDELESLID = RL.RENDELESLID " +
                        "JOIN KONYV K ON RL.KONYVID = K.KONYVID " +
                        "ORDER BY SZAMLA_L.SZAMLALID";
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    invoices.add(getInvoice(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            String sql = "SELECT SZAMLA_F.SZAMLAFID, SZAMLA_F.DATUM_EV, SZAMLA_F.AR, " +
                    "K.CIM, F.TELJES_NEV, F.FELH_CIM " +
                    "FROM SZAMLA_F " +
                    "JOIN RENDELES_F RF ON SZAMLA_F.RENDELESFID = RF.RENDELESFID " +
                    "JOIN KONYV K ON RF.KONYVID = K.KONYVID " +
                    "JOIN FELHASZNALO F ON RF.USERID = F.USERID " +
                    "WHERE RF.USERID = ? " +
                    "ORDER BY SZAMLA_F.SZAMLAFID ";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, uid);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    invoices.add(getInvoice(rs));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return invoices;
    }*/
}
