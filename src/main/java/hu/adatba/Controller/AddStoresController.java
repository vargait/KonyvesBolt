package hu.adatba.Controller;

import hu.adatba.App;
import hu.adatba.Service.StoreService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AddStoresController {

    @FXML
    private TextField storeNameTF, storeAddressTF, storePhoneTF, storeEmailTF;

    @FXML
    private Button saveStoreBTN;

    @FXML
    private Button getBackBTN;

    @FXML
    private Label messageLabel;
    private final StoreService storeService = new StoreService();

    public AddStoresController()throws SQLException{}

    public void initialize(){

        getBackBTN.setOnAction(e-> {
            try {
                switchToListBooks();
            }catch(IOException ex){
                throw new RuntimeException(ex);
            }
        });

        saveStoreBTN.setOnAction(e->{
            try{
                handleSave();
            }catch(IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void handleSave() throws IOException{
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
            }else{
                messageLabel.setText("Sikertelen felvitel!");
            }
        }catch(RuntimeException e){
            messageLabel.setText("Sikertelen mentés!");
        }
    }

    private void switchToListBooks() throws IOException {
        App.setRoot("list_books");
    }
}
