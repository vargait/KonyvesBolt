package hu.adatba.Model;

public class Cart {
    private int kosarID;
    private int userID;
    private int letrehozas_ev;

    public Cart(){}

    public Cart(int kosarID, int userID, int letrehozas_ev) {
        this.kosarID = kosarID;
        this.userID = userID;
        this.letrehozas_ev = letrehozas_ev;
    }

    public int getLetrehozas_ev() {
        return letrehozas_ev;
    }

    public void setLetrehozas_ev(int letrehozas_ev) {
        this.letrehozas_ev = letrehozas_ev;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getKosarID() {
        return kosarID;
    }

    public void setKosarID(int kosarID) {
        this.kosarID = kosarID;
    }

}
