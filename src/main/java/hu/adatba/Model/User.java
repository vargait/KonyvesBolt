package hu.adatba.Model;

public class User {
    // Adattagok
    private int UserID;
    private String Username;
    private String Email;
    private String Password;
    private boolean VIP;
    private String PaymentMethod;
    private String Role;

    // Konstruktor
    public User(String Username, String Email, String Password, int VIP, String PaymentMethod, String Role) {
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.VIP = VIP == 1;
        this.PaymentMethod = PaymentMethod;
        this.Role = Role;
    }

    // Getter - Setter
    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public boolean isVIP() {
        return VIP;
    }

    public void setVIP(boolean VIP) {
        this.VIP = VIP;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
}