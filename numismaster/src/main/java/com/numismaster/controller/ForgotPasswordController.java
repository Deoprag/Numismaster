package com.numismaster.controller;

import java.util.Optional;

import com.numismaster.model.User;
import com.numismaster.service.UserService;
import com.numismaster.util.Email;
import com.numismaster.util.Util;
import com.numismaster.util.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ForgotPasswordController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private User user;
	private String confirmationCode;
	private boolean password = false;
	private double x, y;

	@FXML
	private Button btnClose;
	@FXML
	private Button btnMinimize;
	@FXML
	private Button btnSave;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private PasswordField txtPasswordConfirmation;
	@FXML
	private TextField txtEmail;
	@FXML
	private Label lblWarning;
	@FXML
	private Pane paneBar;
	
	public void initialize(){
		checkInputs();
	}

	public void checkInputs() {
		Util.addTextListener("", 100, txtEmail);
		Util.addTextListener("", 32, txtPassword);
		Util.addTextListener("", 32, txtPasswordConfirmation);
	}

	public void sendConfirmationCode() {
		if (!txtEmail.getText().isBlank()){

			UserService userService = new UserService();
			user = userService.findByEmail(txtEmail.getText());
			confirmationCode = Util.generateCode();
			Email email = new Email();
			System.out.println(confirmationCode);
			try {
				email.sendConfirmationCode(confirmationCode, user.getEmail(), user.getFirstName());
			} catch (Exception e) {
				
			}
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("CONFIRMAÇÃO");
			alert.setHeaderText("Email enviado.");
			alert.setContentText("Caso o email informado esteja cadastrado no sistema, você receberá um código de confirmação!");
			alert.showAndWait();
			txtPassword.setDisable(false);
			txtPasswordConfirmation.setDisable(false);
			btnSave.setDisable(false);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique o email.");
			alert.setContentText("Você precisa preencher o campo de email!");
			alert.showAndWait();
		}
	}

	public boolean checkPassword() {
		if (!Validator.passwordRequirements(txtPassword.getText(), txtPasswordConfirmation.getText(), lblWarning)) {
			return false;
		}
		password = true;
		return true;
	}

	public void changePassword() {
		if (txtPassword.getText().isBlank() || txtPasswordConfirmation.getText().isBlank()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique os campos.");
			alert.setContentText("Você precisa preencher todos os campos!");
			alert.showAndWait();
		}
		if(password){
			UserService userService = new UserService();
			int i = 0;
			do {
				i++;
				TextInputDialog td = new TextInputDialog();
				td.setTitle("Finalizar cadastro. Tentativa: " + i + "/3");
				td.setHeaderText("Insira o código de confirmação enviado no email: "
						+ txtEmail.getText());
				td.setContentText("Código: ");

				Optional<String> result = td.showAndWait();
				if (result.isPresent()) {
					String name = result.get();
					if (confirmationCode.equals(name)) {
						user.setPassword(Util.hashPassword(txtPassword.getText()));
						if(userService.save(user)) {
							Alert alert = new Alert(AlertType.CONFIRMATION);
							alert.setTitle("SUCESSO");
							alert.setHeaderText("Senha alterada.");
							alert.setContentText("A senha foi alterada com sucesso!");

							if (alert.showAndWait().get() == ButtonType.OK) {
								btnClose.fire();
								break;
							}
							break;
						}
					} else {
						if (i >= 3) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("ERRO!");
							alert.setHeaderText("Código incorreto!");
							alert.setContentText(
									"Você errou o código 3 vezes. Infelizmente não foi possivel finalizar o cadastro!");
							alert.showAndWait();
						} else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("ERRO!");
							alert.setHeaderText("Código incorreto!");
							alert.setContentText("Código inválido. Tente novamente!");
							alert.showAndWait();
						}
					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("OBS");
					alert.setHeaderText("Operação cancelada!");
					alert.setContentText("Você cancelou a operação!");
					alert.showAndWait();
					break;
				}
			} while (i < 3);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique a senha!");
			alert.setContentText("A senha não cumpre todos os requisitos.");
			alert.showAndWait();
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
		Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
		stage.close();
	}

	public void minimize(ActionEvent e) {
		Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}
}
