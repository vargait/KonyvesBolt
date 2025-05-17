package hu.adatba.Model;

public class Store {
    private int StoreID;
    private String StoreName;
    private String StoreAddress;
    private String StorePNumber;
    private String email;

    public Store(){}

    public Store(String storeName, String storeAddress, String storePNumber, String email) {
        StoreName = storeName;
        StoreAddress = storeAddress;
        StorePNumber = storePNumber;
        this.email = email;
    }

    public int getStoreID() {
        return StoreID;
    }

    public void setStoreID(int storeID) {
        StoreID = storeID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreAddress() {
        return StoreAddress;
    }

    public void setStoreAddress(String storeAddress) {
        StoreAddress = storeAddress;
    }

    public String getStorePNumber() {
        return StorePNumber;
    }

    public void setStorePNumber(String storePNumber) {
        StorePNumber = storePNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
