package com.numismaster.controller;

import com.numismaster.model.Coin;
import com.numismaster.model.CoinUser;
import com.numismaster.model.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Setter;

@Setter
public class RequestEditorController {

    private AdminMenuController adminMenuController;
    private User user;

    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimize;
    @FXML
    private Pane paneBar;

    private double x, y;


    public void initialize() {
        
    }

    public void loadUser(User newUser, AdminMenuController amc) {
        user = newUser;
        adminMenuController = amc;
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
