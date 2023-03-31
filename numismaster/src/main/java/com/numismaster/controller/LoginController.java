package com.numismaster.controller;

import java.io.IOException;
import java.util.Optional;

import com.numismaster.model.Type;
import com.numismaster.model.User;
import com.numismaster.repository.UserRepository;
import com.numismaster.util.Email;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private User user;

	@FXML
	private Button btnSignIn;
	@FXML
	private Button btnSignUp;
	@FXML
	private Button btnClose;
	@FXML
	private Button btnMinimize;
	@FXML
	private Button btnHelp;
	@FXML
	private Button btnForgotPassword;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Pane paneBar;
	@FXML
	private ImageView dbImage;

	private double x, y;

	public void initialize() {
		if (Util.isDatabaseOn()) {
			dbImage.setImage(new Image("/com/numismaster/icon/database-on.png"));
		} else {

		}
	}

	@FXML
	public void checkUsernameInput(KeyEvent e) {
		txtUsername.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^a-zA-Z0-9]", "");

			if (!newValue.equals(filteredValue)) {
				txtUsername.setText(filteredValue);
			}
		});
	}

	public void signIn(ActionEvent e) throws IOException {
		UserRepository ur = new UserRepository();
		if (txtUsername.getText().isBlank() || txtPassword.getText().isBlank()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique os campos.");
			alert.setContentText("Você precisa preencher todos os campos!");
			alert.showAndWait();
		} else {
			user = ur.login(txtUsername.getText(), txtPassword.getText());
			if (user != null) {
				if (user.isBlocked()) {
					String code = Util.generateCode();
					if (Email.sendEmail(code, user.getEmail(), user.getFirstName())) {
						int i = 0;
						do {
							i++;
							TextInputDialog td = new TextInputDialog();
							td.setTitle("USUARIO BLOQUEADO! Tentativa: " + i + "/3");
							td.setHeaderText("Insira o código de confirmação enviado no email: "
									+ Util.mockEmail(user.getEmail()));
							td.setContentText("Código: ");

							Optional<String> result = td.showAndWait();
							if (result.isPresent()) {
								String name = result.get();
								if (code.equals(name)) {
									user.setBlocked(false);
									if (ur.update(user)) {
										Alert alert = new Alert(AlertType.CONFIRMATION);
										alert.setTitle("SUCESSO!");
										alert.setHeaderText("Usuário desbloqueado com sucesso!");
										alert.setContentText("Agora você poderá efetuar seu login no sistema.");
										alert.showAndWait();
									}
									break;
								}
							} else {
								break;
							}
							if (i >= 3) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("ERRO!");
								alert.setHeaderText("Código incorreto!");
								alert.setContentText(
										"Você errou o código 3 vezes. Infelizmente não foi possivel desbloquear seu acesso!");
								alert.showAndWait();
								txtUsername.setText("");
								txtPassword.setText("");
							}
						} while (i < 3);
					}
				} else if (user.getType().equals(Type.ADMIN)) {
					registerItens(e);
				} else {
					myCoins(e);
				}
			} else {
				txtPassword.setText("");
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERRO!");
				alert.setHeaderText("Dados incorretos.");
				alert.setContentText("Verifique se seu nome de usuário e senha estão corretos");
				alert.showAndWait();
			}
		}
	}

	public void myCoins(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/numismaster/view/MyCoins.fxml"));
		root = loader.load();
		MyCoinsController mcc = loader.getController();
		mcc.loadUser(user);
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.getIcons().add(new Image("/com/numismaster/icon/large-app-icon.png"));
		stage.setScene(scene);
		stage.show();
	}

	public void registerItens(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/numismaster/view/RegisterItens.fxml"));
		root = loader.load();
		RegisterItensController ric = loader.getController();
		ric.loadUser(user);
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.getIcons().add(new Image("/com/numismaster/icon/large-app-icon.png"));
		stage.setScene(scene);
		stage.show();
	}

	public void signUp(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/numismaster/view/SignUp.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.getIcons().add(new Image("/com/numismaster/icon/large-app-icon.png"));
		stage.setScene(scene);
		stage.show();
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

	public void help() {
		Util.openWebpage("mailto:deopraglabs@gmail.com?subject=Suporte%20-%20Numismaster");
	}
}
