package com.numismaster.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.sql.rowset.serial.SerialException;

import com.numismaster.javafx.MaskTextField;
import com.numismaster.model.Gender;
import com.numismaster.model.Type;
import com.numismaster.model.User;
import com.numismaster.service.UserService;
import com.numismaster.util.Email;
import com.numismaster.util.Util;
import com.numismaster.util.Validator;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SignUpController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	private User user = new User();

	@FXML
	private Button btnSignUp;
	@FXML
	private Button btnClose;
	@FXML
	private Button btnMinimize;
	@FXML
	private Button btnReturn;
	@FXML
	private Button btnFileChooser;
	@FXML
	private Pane paneBar;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private DatePicker txtBirthDate;
	@FXML
	private TextField txtCpf;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPassword;
	@FXML
	private TextField txtPasswordConfirmation;
	@FXML
	private ChoiceBox<Gender> boxGender;
	@FXML
	private ImageView imageView;
	@FXML
	private Label lblWarning;
	@FXML
	private Label lblSelectedFile;

	private double x, y = 0;

	public void initialize() {
		datePickerInitializer();
		boxGender.getItems().add(Gender.Feminino);
		boxGender.getItems().add(Gender.Masculino);
		boxGender.getItems().add(Gender.Outro);
		try {
			File file = new File("numismaster/src/main/java/com/numismaster/icon/user.png");
			FileInputStream fis = new FileInputStream(file);
			user.setProfilePhoto(Util.convertToBlob(fis));
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkInputs();
	}

	public void checkInputs() {
		txtFirstName.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^a-z A-Z]", "");
			if (!newValue.equals(filteredValue)) {
				txtFirstName.setText(filteredValue);
			}
		});
		txtLastName.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^a-z A-Z]", "");
			if (!newValue.equals(filteredValue)) {
				txtLastName.setText(filteredValue);
			}
		});
		txtUsername.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^a-zA-Z0-9]", "");
			if (!newValue.equals(filteredValue)) {
				txtUsername.setText(filteredValue);
			}
		});
	}

	public boolean validateSignUpFields() {
		if (txtFirstName.getText().isBlank() || txtLastName.getText().isBlank() ||
				txtBirthDate.getValue().toString().isBlank() || txtCpf.getText().isBlank() ||
				boxGender.getValue() == null || txtUsername.getText().isBlank() ||
				txtEmail.getText().isBlank() || boxGender.getValue().toString().isBlank() ||
				txtPassword.getText().isBlank() || txtPasswordConfirmation.getText().isBlank()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("OPS...");
			alert.setHeaderText("Verifique os campos.");
			alert.setContentText("Você precisa preencher todos os campos!");
			alert.showAndWait();
			return false;
		}
		if (!checkPassword()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("OPS...");
			alert.setHeaderText("Senha inválida.");
			alert.setContentText("Verifique sua senha e tente novamente!");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public void barPressed(MouseEvent e) {
		x = e.getSceneX();
		y = e.getSceneY();
	}

	public boolean checkPassword() {
		if (!Validator.passwordRequirements(txtPassword.getText(), txtPasswordConfirmation.getText(), lblWarning)) {
			return false;
		}
		return true;
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

	public void returnToLogin(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/numismaster/view/Login.fxml"));
		root = loader.load();
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void chooseFile(ActionEvent e) throws SerialException, SQLException {
		FileChooser fc = new FileChooser();
		fc.setTitle("Selecionar imagem");
		fc.setInitialDirectory(null);
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
		File file = fc.showOpenDialog(btnFileChooser.getScene().getWindow());
		try {
			if(file != null){
				if (file.length() <= 2 * 1024 * 1024) {
					FileInputStream fis = new FileInputStream(file);
					user.setProfilePhoto(Util.convertToBlob(fis));
					lblSelectedFile.setText(file.getName().toString());
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Erro ao selecionar arquivo.");
					alert.setHeaderText("Arquivo muito grande!");
					alert.setContentText("O arquivo selecionado é maior do que 2MB. Selecione um arquivo menor.");
					alert.showAndWait();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void signUp(ActionEvent e) throws IOException {
		if (validateSignUpFields()) {
			UserService userService = new UserService();
			user.setFirstName(Util.capitalizeString(txtFirstName.getText()));
			user.setLastName(Util.capitalizeString(txtLastName.getText()));
			user.setBirthDate(txtBirthDate.getValue());
			user.setCpf(txtCpf.getText().replace(".", "").replace("-", ""));
			user.setEmail(txtEmail.getText());
			user.setGender(boxGender.getValue());
			user.setUsername(txtUsername.getText());
			user.setPassword(Util.hashPassword(txtPassword.getText()));
			user.setBlocked(false);
			user.setType(Type.Default);
			if(userService.save(user)){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("SUCESSO!");
				alert.setHeaderText("Usuário cadastrado com sucesso!");
				alert.setContentText("Agora você poderá efetuar seu login no sistema.");
				alert.showAndWait();
				returnToLogin(e);
			}
		}
	}

	public void datePickerInitializer() {
		txtBirthDate.setValue(LocalDate.now());
		txtBirthDate.setConverter(new StringConverter<LocalDate>() {
			@Override
			public String toString(LocalDate localdate) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				return dtf.format(localdate);
			}

			@Override
			public LocalDate fromString(String arg0) {
				return null;
			}
		});

	}

	@FXML
	private void checkCpf() {
		MaskTextField mtf = new MaskTextField();
		mtf.setMask("###.###.###-##");
		mtf.setValidCharacters("0123456789");
		mtf.setTf(txtCpf);
		mtf.formatter();
	}
}
