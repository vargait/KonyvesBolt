package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Model.Book;
import hu.adatba.Model.Store;
import hu.adatba.Model.StoreStock;
import hu.adatba.Service.BookService;
import hu.adatba.Service.StoreService;
import hu.adatba.Service.StoreStockService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AddStoresController {

    @FXML
    private TextField storeNameTF, storeAddressTF, storePhoneTF, storeEmailTF;

    @FXML
    private Spinner<Integer> addstockstoreidSP, addstockbookidSP, addstockquantitySP;

    @FXML
    private Button saveStoreBTN, getBackBTN;

    @FXML
    private Button saveStockBTN;

    @FXML
    TableView<Store> listStoreTV;

    @FXML
    TableView<StoreStock> listStoreStockTV;

    @FXML
    TableColumn<Store, Integer> listStoreIDTC;

    @FXML
    TableColumn<Store, Integer> listStoreStockBookIDTC, listStoreStockStoreIDTC, listStoreStockQuantityTC;

    @FXML
    TableColumn<Store, String> listStoreNameTC, listStoreAddressTC, listStorePhoneTC, listStoreEmailTC;


    @FXML
    private Label messageLabel;
    private final StoreService storeService = new StoreService();
    private final BookService bookService = new BookService();
    private final StoreStockService storeStockService = new StoreStockService();

    public AddStoresController()throws SQLException{}

    public void initialize(){

        //Mezők értékadása
        ObservableList<Integer> options;
        SpinnerValueFactory<Integer> valueFactory;
        options = FXCollections.observableArrayList(storeService.getStores());
        valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        addstockstoreidSP.setValueFactory(valueFactory);

        options = FXCollections.observableArrayList(bookService.getBooks());
        valueFactory = new SpinnerValueFactory.ListSpinnerValueFactory<>(options);
        addstockbookidSP.setValueFactory(valueFactory);

        addstockquantitySP.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        addstockquantitySP.setEditable(true);

        //Áruház Lista feltöltése
        listStoreIDTC.setCellValueFactory(new PropertyValueFactory<>("StoreID"));
        listStoreNameTC.setCellValueFactory(new PropertyValueFactory<>("StoreName"));
        listStoreAddressTC.setCellValueFactory(new PropertyValueFactory<>("StoreAddress"));
        listStorePhoneTC.setCellValueFactory(new PropertyValueFactory<>("StorePNumber"));
        listStoreEmailTC.setCellValueFactory(new PropertyValueFactory<>("email"));
        List<Store> stores = storeService.getAllStores();
        listStoreTV.setItems(FXCollections.observableArrayList(stores));

        //ÁruházTételek lista feltöltése
        listStoreStockBookIDTC.setCellValueFactory(new PropertyValueFactory<>("BookID"));
        listStoreStockStoreIDTC.setCellValueFactory(new PropertyValueFactory<>("ShopID"));
        listStoreStockQuantityTC.setCellValueFactory(new PropertyValueFactory<>("onStock"));
        List<StoreStock> storeStocks = storeStockService.getStoreStocks();
        listStoreStockTV.setItems(FXCollections.observableArrayList(storeStocks));


        //Gombok
        saveStockBTN.setOnAction(e->{
            try{
                handleSaveStock();
            }catch(IOException exx){
                throw new RuntimeException(exx);
            }
        });

        getBackBTN.setOnAction(e-> {
            try {
                switchToListBooks();
            }catch(IOException ex){
                throw new RuntimeException(ex);
            }
        });

        saveStoreBTN.setOnAction(e->{
            try{
                handleSaveStore();
            }catch(IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void handleSaveStore() throws IOException{
        try{
            String nev = storeNameTF.getText();
            String cim = storeAddressTF.getText();
            String telefonszam = storePhoneTF.getText();
            String email = storeEmailTF.getText();

            if(nev.isEmpty() || cim.isEmpty() || telefonszam.isEmpty()|| email.isEmpty()){
                messageLabel.setText("Helytelen adatok!");
                return;
            }
            if(storeService.addStore(nev, cim, telefonszam, email)){
                messageLabel.setText("Sikeres felvitel!");
                List<Store> stores = storeService.getAllStores();
                listStoreTV.setItems(FXCollections.observableArrayList(stores));
            }else{
                messageLabel.setText("Sikertelen felvitel!");
            }
        }catch(RuntimeException e){
            messageLabel.setText("Sikertelen mentés!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleSaveStock() throws IOException{
        try{
            int StoreID = addstockstoreidSP.getValue();
            int BookID = addstockbookidSP.getValue();
            int Quantity = addstockquantitySP.getValue();

            if(Quantity<1){
                messageLabel.setText("Nem lehet 0 db tételt adni az Aruhazhoz!");
            }
            if(storeStockService.addStoreStock(StoreID, BookID, Quantity)){
                List<StoreStock> storeStocks = storeStockService.getStoreStocks();
                listStoreStockTV.setItems(FXCollections.observableArrayList(storeStocks));
                messageLabel.setText("Sikeres felvitel!");
            }else{
                messageLabel.setText("Sikertelen felvitel!");
            }
        }catch (RuntimeException err){
            messageLabel.setText("Sikertelen mentes!");
        }
    }

    private void switchToListBooks() throws IOException {
        App.setRoot("list_books");
    }
}
