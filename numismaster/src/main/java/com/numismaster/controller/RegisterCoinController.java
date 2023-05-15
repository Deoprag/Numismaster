package com.numismaster.controller;

import com.numismaster.model.Coin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Setter;

@Setter
public class RegisterCoinController {
    
    private Coin coin;

    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimize;
    @FXML
	private Pane paneBar;
    
    private double x, y;
    
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnUpdate;
    
    public void registerCoin(){

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
