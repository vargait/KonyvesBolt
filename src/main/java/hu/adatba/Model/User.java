package hu.adatba.Model;

public class User {
    // Adattagok
    private int UserID;
    private String Username;
    private String Email;
    private String Password;
    private boolean VIP;
    private String FullName;
    private String PostalAddress;
    private String CreditNumber;
    private String Role;
    private String ipAddress;

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

    public User(String ipAddress){
        this.ipAddress = ipAddress;
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

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPostalAddress() {
        return PostalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        PostalAddress = postalAddress;
    }

    public String getCreditNumber() {
        return CreditNumber;
    }

    public void setCreditNumber(String creditNumber) {
        CreditNumber = creditNumber;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }
}