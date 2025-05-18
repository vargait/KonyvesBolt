package hu.adatba.DAO;

import hu.adatba.Model.Invoice;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public Invoice getInvoice(ResultSet rs) throws SQLException {
        return new Invoice(
                rs.getInt(1),
                rs.getString(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getString(6),
                rs.getInt(7)
        );
    }

    // Összes számla lekérdezése - Összetett lekérdezések
    public List<Invoice> getAllInvoices(int UserID) throws SQLException {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT  " +
                "    r.RENDELESID, " +
                "    r.FELHASZNALONEV, " +
                "    r.RENDELES_DATUM, " +
                "    r.SZAMLAZASI_CIM, " +
                "    r.KARTYASZAM, " +
                "    COUNT(rt.KONYVID) AS TETELEK_SZAMA, " +
                "    SUM(NVL(rt.MENNYISEG, 0) * NVL(rt.EGYSEGAR, 0)) AS OSSZEG " +
                "FROM  " +
                "    RENDELES r " +
                "LEFT JOIN  " +
                "    RENDELESTETEL rt ON r.RENDELESID = rt.RENDELESID " +
                "WHERE  " +
                "    r.USERID = ? " +
                "GROUP BY " +
                "    r.RENDELESID, r.FELHASZNALONEV, r.RENDELES_DATUM, r.SZAMLAZASI_CIM, r.KARTYASZAM " +
                "ORDER BY " +
                "    r.RENDELES_DATUM DESC";
        try (Connection conn = DBConnect.getConnection()){
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, UserID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    invoices.add(getInvoice(rs));
                }
            }
        }
        return invoices;
    }
}
