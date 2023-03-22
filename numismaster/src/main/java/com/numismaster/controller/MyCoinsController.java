package com.numismaster.controller;

import com.numismaster.model.TableCoin;
import com.numismaster.model.Type;
import com.numismaster.model.User;
import com.numismaster.util.Util;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Setter;

@Setter
public class MyCoinsController {

	private User user;

	@FXML private Button btnClose;
	@FXML private Button btnMinimize;
	@FXML private Pane paneBar;
	@FXML private ImageView profilePhoto;
	@FXML private Label lblName;
	@FXML private TableView<TableCoin> tableCoins;
	@FXML private TableColumn<TableCoin, ImageView> imgFront;
	@FXML private TableColumn<TableCoin, ImageView> imgBack;
	@FXML private TableColumn<TableCoin, String> name;
	@FXML private TableColumn<TableCoin, String> country;
	@FXML private TableColumn<TableCoin, Short> year;
	@FXML private TableColumn<TableCoin, String> condition;
	@FXML private TableColumn<TableCoin, String> rarity;
	@FXML private TableColumn<TableCoin, Character> isForSale;
	@FXML private TableColumn<TableCoin, Float> price;
	@FXML private TableColumn<TableCoin, Button> details;
    
    private double x, y = 0;

	public void initialize(){
		fixImage(profilePhoto, true);
		loadCoins();
	}

	public void loadUser(User newUser){
		user = newUser;
		lblName.setText(user.getFirstName() + " " + user.getLastName());
		if(user.getType().equals(Type.ADMIN)){
			lblName.setTextFill(Color.RED);
		}
		try {
			profilePhoto.setImage(new Image(Util.convertFromBlob(user.getProfilePhoto())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fixImage(ImageView image, boolean circle){
		image.setFitWidth(150);
		image.setFitHeight(150);

		if (circle){
			Circle clip = new Circle();
			clip.setCenterX(75);
			clip.setCenterY(75);
			clip.setRadius(75);

			image.setClip(clip);
			image.setSmooth(true);
			image.setCache(true);
		}
	}

	public void loadCoins(){

	}

    public void barPressed(MouseEvent e) {
		x = e.getSceneX();
		y = e.getSceneY();
	}

    public void barDragged(MouseEvent e) {
		Stage stage = (Stage)((Pane)e.getSource()).getScene().getWindow();
		stage.setY(e.getScreenY() - y);
		stage.setX(e.getScreenX() - x);
	}
	
	public void close(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sair");
		alert.setHeaderText("Você está saindo!");
		alert.setContentText("Tem certeza que deseja sair?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {
			Stage stage = (Stage)((Button)e.getSource()).getScene().getWindow();
			stage.close();
		}
	}
	
	public void minimize(ActionEvent e) {
		Stage stage = (Stage)((Button)e.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

}
