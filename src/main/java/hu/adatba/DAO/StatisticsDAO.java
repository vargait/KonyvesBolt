package hu.adatba.DAO;

import hu.adatba.Model.QueryResult;
import hu.adatba.db.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticsDAO {
    public StatisticsDAO() {}

    // ÖSSZETETT LEKÉRDEZÉSEK

    // +10 féle összetett lekérdezés megvalósítása statisztika jelleggel - admin
    public List<QueryResult> getQueryResults(int queryIndex) {
        String sql = "";
        switch (queryIndex) {
            case 1: // Műfajonként hány könyv van
                sql = "SELECT m.mufajneV, COUNT(k.konyvID) AS konyv_darab " +
                        "FROM mufaj m " +
                        "JOIN konyv k ON m.mufajID = k.mufajID " +
                        "GROUP BY m.mufajneV ";
                break;

            case 2: // Felhasználók rendelési száma
                sql = "SELECT f.teljes_nev, COUNT(r.rendelesID) AS rendelesek_szama " +
                        "FROM felhasznalo f " +
                        "JOIN rendeles r ON f.userID = r.userID " +
                        "GROUP BY f.teljes_nev ";
                break;

            case 3: // TOP 5 legtöbbet rendelt könyv
                sql = "SELECT k.cim, SUM(rt.mennyiseg) AS rendeles_ossz " +
                        "FROM rendelestetel rt " +
                        "JOIN konyv k ON rt.konyvID = k.konyvID " +
                        "GROUP BY k.cim " +
                        "ORDER BY rendeles_ossz DESC " +
                        "FETCH FIRST 5 ROWS ONLY ";
                break;

            case 4: // Törzsvásárlók (legalább 3 rendelés)
                sql = "SELECT f.teljes_nev, COUNT(r.rendelesID) AS rendeles_szam " +
                        "FROM felhasznalo f " +
                        "JOIN rendeles r ON f.userID = r.userID " +
                        "GROUP BY f.teljes_nev " +
                        "HAVING COUNT(r.rendelesID) >= 3 ";
                break;

            case 5: // Rendelések összértéke havonta
                sql = "SELECT " +
                        "  SUBSTR(TO_CHAR(r.rendeles_datum), 1, 6) AS ev, " +
                        "  SUM(TO_NUMBER(r.vegosszeg)) AS osszes_bevetel " +
                        "FROM rendeles r " +
                        "GROUP BY SUBSTR(TO_CHAR(r.rendeles_datum), 1, 6) " +
                        "ORDER BY ev ";
                break;
            case 6: // Átlagos könyvár műfajonként
                sql = "SELECT m.mufajneV, AVG(k.ar) AS atlag_ar " +
                        "FROM mufaj m " +
                        "JOIN konyv k ON m.mufajID = k.mufajID " +
                        "GROUP BY m.mufajneV";
                break;
            case 7: // Legtöbbet választott könyv
                sql = "SELECT k.cim, COUNT(*) AS darab " +
                        "FROM rendelestetel rt " +
                        "JOIN konyv k ON rt.konyvID = k.konyvID " +
                        "GROUP BY k.cim " +
                        "ORDER BY darab DESC " +
                        "FETCH FIRST 1 ROWS ONLY ";
                break;
            case 8: // Csak egy rendeléses felhasználók
                sql = "SELECT userid, teljes_nev FROM felhasznalo " +
                        "WHERE userID IN ( " +
                        "  SELECT userID FROM rendeles " +
                        "  GROUP BY userID " +
                        "  HAVING COUNT(*) = 1 " +
                        ") ";
                break;
            case 9: // Felhasználók összes költése
                sql = "SELECT f.teljes_nev, SUM(r.vegosszeg) AS osszes_koltese " +
                        "FROM felhasznalo f " +
                        "JOIN rendeles r ON f.userID = r.userID " +
                        "GROUP BY f.teljes_nev " +
                        "ORDER BY osszes_koltese DESC ";
                break;
        }


        List<QueryResult> result = new ArrayList<>();
        try (Connection conn = DBConnect.getConnection()){
            try {
                assert conn != null;
                try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        QueryResult qr = new QueryResult(
                                rs.getString(1),
                                rs.getString(2)
                        );
                        result.add(qr);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
