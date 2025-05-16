package hu.adatba.Model;

public class User {
    // Adattagok
    private int UserID;
    private String Username;
    private String Password;
    private String Email;
    private String FullName;
    private boolean VIP;
    private String PostalAddress;
    private String CreditNumber;
    private String Role;

    // Konstruktor
    public User(String Username, String Email, String Password, int VIP, String FullName, String PostalAddress, String CreditNumber) {
        this.Username = Username;
        this.Email = Email;
        this.Password = Password;
        this.VIP = VIP == 1;
        this.FullName = FullName;
        this.PostalAddress = PostalAddress;
        this.CreditNumber = CreditNumber;
    }

    // Látogató
    public User(){}

    // Getter - Setter
    public int getUserID() {
        return UserID;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return Email;
    }

    public String getFullName() {
        return FullName;
    }

    public boolean isVIP() {
        return VIP;
    }

    public String getPostalAddress() {
        return PostalAddress;
    }

    public String getCreditNumber() {
        return CreditNumber;
    }

    public String getRole() {
        return Role;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public void setVIP(boolean VIP) {
        this.VIP = VIP;
    }

    public void setPostalAddress(String postalAddress) {
        PostalAddress = postalAddress;
    }

    public void setCreditNumber(String creditNumber) {
        CreditNumber = creditNumber;
    }

    public void setRole(String role) {
        Role = role;
    }
}