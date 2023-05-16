package com.numismaster.controller;

import com.numismaster.model.Coin;
import com.numismaster.model.CoinCondition;
import com.numismaster.model.CoinUser;
import com.numismaster.model.User;
import com.numismaster.service.CoinUserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Setter;

@Setter
public class RegisterCoinController {
    
    private Coin coin;
    private User user;

    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimize;
    @FXML
	private Pane paneBar;

    private double x, y;
    
    @FXML
    private Label lblCoinName;
    @FXML
    private TextField txtCoinYear;
    @FXML
    private ChoiceBox<CoinCondition> boxCondition;
    @FXML
    private CheckBox checkForSale;
    @FXML
    private TextField txtCoinPrice;
    @FXML
    private Button btnSelectFrontImage;
    @FXML
    private Button btnSelectBackImage;
    @FXML
    private TextArea txtNotes;

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnUpdate;

    public void initialize(){
        boxCondition.getItems().addAll(CoinCondition.values());
    }

    public void loadCoin(Coin newCoin){
        coin = newCoin;
        lblCoinName.setText(coin.getName().toUpperCase());
    }

    public void loadUser(User newUser){
        user = newUser;
    }
    
    public void registerCoin(){
        CoinUserService coinUserService = new CoinUserService();
        CoinUser coinUser = new CoinUser();
        coinUser.setUser(user);
        coinUser.setCoin(coin);
        coinUser.setYear(Short.parseShort("0"));
        coinUser.setCoinCondition(CoinCondition.Boa);
        coinUser.setForSale(false);
        if(coinUser.isForSale()){
            coinUser.setPrice(Float.parseFloat("0"));
        }
        coinUser.setImageFront(null);
        coinUser.setImageBack(null);
    }

    public void changeTxtPrice(){
        if(checkForSale.isSelected()){
            txtCoinPrice.setDisable(false);
        } else {
            txtCoinPrice.setDisable(true);
        }
    }

    public void chooseFile(){

    }

    public void deleteCoin(){

    }
    
    public void updateCoin(){
        
    }

    public void barPressed(MouseEvent e) {
        x = e.getSceneX();
        y = e.getSceneY();
    }
    
    public void barDragged(MouseEvent e) {
        Stage stage = (Stage) ((Pane) e.getSource()).getScene().getWindow();
        stage.setY(e.getScreenY() - y);
        stage.setX(e.getScreenX() - x);
    }
    
    public void close(ActionEvent e) {
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }
}
