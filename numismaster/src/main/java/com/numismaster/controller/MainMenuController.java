package com.numismaster.controller;

import java.io.IOException;

import com.numismaster.model.Coin;
import com.numismaster.model.CoinUser;
import com.numismaster.model.Type;
import com.numismaster.model.User;
import com.numismaster.util.Util;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class MainMenuController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private User user;

	@FXML
	private Button btnClose;
	@FXML
	private Button btnMinimize;
	@FXML
	private Button btnLogout;
	@FXML
	private Pane paneBar;
	@FXML
	private ImageView profilePhoto;
	@FXML
	private Label lblName;

	//	MyCollection
	@FXML
	private TableView<CoinUser> tbCoinUser;
	@FXML
	private TableColumn<CoinUser, ImageView> colImgFront = new TableColumn<>("Imagem Frontal");
	@FXML
	private TableColumn<CoinUser, ImageView> colImgBack = new TableColumn<>("Imagem Traseira");
	@FXML
	private TableColumn<Coin, String> colName = new TableColumn<>("Nome");
	@FXML
	private TableColumn<Coin, String> colCountry = new TableColumn<>("País");
	@FXML
	private TableColumn<CoinUser, Short> colYear = new TableColumn<>("Ano");
	@FXML
	private TableColumn<CoinUser, String> colCondition = new TableColumn<>("Condição");
	@FXML
	private TableColumn<CoinUser, String> colRarity = new TableColumn<>("Raridade");
	@FXML
	private TableColumn<CoinUser, Character> colIsForSale = new TableColumn<>("A venda?");
	@FXML
	private TableColumn<CoinUser, Float> colPrice = new TableColumn<>("Preço");

	//	Coins


	//	Market

	private double x, y = 0;

	public void initialize() {
		fixImage(profilePhoto, true);
	}

	public void loadUser(User newUser) {
		user = newUser;
		lblName.setText(user.getFirstName() + " " + user.getLastName());
		if (user.getType().equals(Type.Admin)) {
			lblName.setTextFill(Color.rgb(255, 85, 85));
		}
		try {
			profilePhoto.setImage(new Image(Util.convertFromBlob(user.getProfilePhoto())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fixImage(ImageView image, boolean circle) {
		image.setFitWidth(150);
		image.setFitHeight(150);

		if (circle) {
			Circle clip = new Circle();
			clip.setCenterX(75);
			clip.setCenterY(75);
			clip.setRadius(75);

			image.setClip(clip);
			image.setSmooth(true);
			image.setCache(true);
		}
	}

	public void logout(ActionEvent e) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Encerrar Sessão");
		alert.setHeaderText("Você está saindo!");
		alert.setContentText("Tem certeza que deseja encerrar sessão?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/numismaster/view/Login.fxml"));
			root = loader.load();
			stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
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
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sair");
		alert.setHeaderText("Você está saindo!");
		alert.setContentText("Tem certeza que deseja sair?");

		if (alert.showAndWait().get() == ButtonType.OK) {
			Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
			stage.close();
		}
	}

	public void minimize(ActionEvent e) {
		Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

}
