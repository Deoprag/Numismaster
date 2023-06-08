package com.numismaster.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.numismaster.javafx.MaskTextField;
import com.numismaster.javafx.NumismasterCheckComboBox;
import com.numismaster.model.Coin;
import com.numismaster.model.CoinUserSale;
import com.numismaster.model.Country;
import com.numismaster.model.Edge;
import com.numismaster.model.Gender;
import com.numismaster.model.Material;
import com.numismaster.model.Rarity;
import com.numismaster.model.Shape;
import com.numismaster.model.User;
import com.numismaster.model.UserRequest;
import com.numismaster.service.CoinService;
import com.numismaster.service.CoinUserSaleService;
import com.numismaster.service.CountryService;
import com.numismaster.service.EdgeService;
import com.numismaster.service.MaterialService;
import com.numismaster.service.ShapeService;
import com.numismaster.service.UserRequestService;
import com.numismaster.util.Util;
import com.numismaster.util.Validator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminMenuController {

	private Stage stage;
	private Stage stageRequestEditor;
	private Scene scene;
	private Parent root;
	private User user;
	private List<Shape> shapeList;
	private List<Edge> edgeList;
	private List<Material> materialList;
	private List<Country> countryList;
	private Coin coin;
	private Country country;
	private Shape shape;
	private Material material;
	private Edge edge;
	private UserRequest request;

	private double x, y = 0;
	private int clickCount = 0;

	private CoinService coinService = new CoinService();
	private CountryService countryService = new CountryService();
	private ShapeService shapeService = new ShapeService();
	private MaterialService materialService = new MaterialService();
	private EdgeService edgeService = new EdgeService();
	private UserRequestService userRequestService = new UserRequestService();
	private CoinUserSaleService coinUserSaleService = new CoinUserSaleService();

	ObservableList<Coin> obsCoinList = FXCollections.observableArrayList();
	ObservableList<Country> obsCountryList = FXCollections.observableArrayList();
	ObservableList<Shape> obsShapeList = FXCollections.observableArrayList();
	ObservableList<Material> obsMaterialList = FXCollections.observableArrayList();
	ObservableList<Edge> obsEdgeList = FXCollections.observableArrayList();
	ObservableList<UserRequest> obsRequestList = FXCollections.observableArrayList();
	ObservableList<CoinUserSale> obsCoinUserSaleList = FXCollections.observableArrayList();

	@FXML
	private Button btnClose;
	@FXML
	private Button btnMinimize;
	@FXML
	private Pane paneBar;
	@FXML
	private ImageView profilePhoto;
	@FXML
	private Label lblName;

	// Coin
	@FXML
	private Pane paneCoin;
	@FXML
	private Button btnRegisterCoin;
	@FXML
	private Button btnDeleteCoin;
	@FXML
	private Button btnUpdateCoin;
	@FXML
	private TextField txtCoinName;
	@FXML
	private TextField txtDenomination;
	@FXML
	private TextField txtWeight;
	@FXML
	private TextField txtDiameter;
	@FXML
	private TextField txtThickness;
	@FXML
	private TextField txtCoinSearch;
	@FXML
	private ComboBox<Rarity> boxRarity;
	@FXML
	private ComboBox<String> boxCountry;
	@FXML
	private NumismasterCheckComboBox<String> boxShape;
	@FXML
	private NumismasterCheckComboBox<String> boxMaterial;
	@FXML
	private NumismasterCheckComboBox<String> boxEdge;
	@FXML
	private TableView<Coin> tbCoin;
	@FXML
	private TableColumn<Coin, String> colCoinName = new TableColumn<>("Nome");
	@FXML
	private TableColumn<Coin, Integer> colDenomination = new TableColumn<>("Valor de Face");
	@FXML
	private TableColumn<Coin, Float> colWeight = new TableColumn<>("Peso(g)");
	@FXML
	private TableColumn<Coin, Float> colDiameter = new TableColumn<>("Diâmetro(mm)");
	@FXML
	private TableColumn<Coin, Float> colThickness = new TableColumn<>("Grossura(mm)");
	@FXML
	private TableColumn<Coin, Rarity> colRarity = new TableColumn<>("Raridade");
	@FXML
	private TableColumn<Coin, String> colCountry = new TableColumn<>("País");
	@FXML
	private TableColumn<Coin, List<String>> colShape = new TableColumn<>("Formatos");
	@FXML
	private TableColumn<Coin, List<String>> colMaterial = new TableColumn<>("Materiais");
	@FXML
	private TableColumn<Coin, List<String>> colEdge = new TableColumn<>("Bordas");

	// Country
	@FXML
	private Button btnRegisterCountry;
	@FXML
	private Button btnDeleteCountry;
	@FXML
	private TextField txtCountryCode;
	@FXML
	private TextField txtCountryName;
	@FXML
	private TextField txtCountrySearch;
	@FXML
	private Button btnUpdateCountry;
	@FXML
	private TableView<Country> tbCountry;
	@FXML
	private TableColumn<Country, String> colCountryCode = new TableColumn<>("Código");
	@FXML
	private TableColumn<Country, String> colCountryName = new TableColumn<>("Nome");

	// Shape
	@FXML
	private Button btnRegisterShape;
	@FXML
	private Button btnDeleteShape;
	@FXML
	private Button btnUpdateShape;
	@FXML
	private TextField txtShapeName;
	@FXML
	private TextField txtShapeSearch;
	@FXML
	private TableView<Shape> tbShape;
	@FXML
	private TableColumn<Shape, String> colShapeName = new TableColumn<>("Formato");

	// Material
	@FXML
	private Button btnRegisterMaterial;
	@FXML
	private Button btnDeleteMaterial;
	@FXML
	private Button btnUpdateMaterial;
	@FXML
	private TextField txtMaterialName;
	@FXML
	private TextField txtMaterialSearch;
	@FXML
	private TableView<Material> tbMaterial;
	@FXML
	private TableColumn<Material, String> colMaterialName = new TableColumn<>("Material");

	// Edge
	@FXML
	private Button btnRegisterEdge;
	@FXML
	private Button btnDeleteEdge;
	@FXML
	private Button btnUpdateEdge;
	@FXML
	private TextField txtEdgeName;
	@FXML
	private TextField txtEdgeSearch;
	@FXML
	private TableView<Edge> tbEdge;
	@FXML
	private TableColumn<Edge, String> colEdgeName = new TableColumn<>("Borda");

	// User
	@FXML
	private Button btnRegisterUser;
	@FXML
	private Button btnDeleteUser;
	@FXML
	private Button btnUpdateUser;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private DatePicker txtBirthDate;
	@FXML
	private TextField txtCpf;
	@FXML
	private ChoiceBox<Gender> boxGender;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	@FXML
	private TextField txtPasswordConfirmation;
	@FXML
	private TextField txtEmail;
	@FXML
	private Button btnFileChooser;
	@FXML
	private Label lblSelectedFile;
	@FXML
	private Label lblWarning;

	// Request
	@FXML
	private TextField txtRequestSearch;
	@FXML
	private TableView<UserRequest> tbRequest;
	@FXML
	private TableColumn<UserRequest, String> colRequestingPerson = new TableColumn<>("Solicitante");
	@FXML
	private TableColumn<UserRequest, String> colRequestedItem = new TableColumn<>("Item");
	@FXML
	private TableColumn<UserRequest, String> colRequestNotes = new TableColumn<>("Notas");
	@FXML
	private TableColumn<UserRequest, String> colRequestSituation = new TableColumn<>("Situação");
	@FXML
	private TableColumn<UserRequest, String> colRequestDate = new TableColumn<>("Data de Solicitação");
	@FXML
	private TableColumn<UserRequest, LocalDateTime> colCloseRequestDate = new TableColumn<>("Data de Fechamento");
	@FXML
	private TextField txtRequestUser;
	@FXML
	private TextField txtRequestItem;
	@FXML
	private TextArea txtRequestNotes;

	// Transactions
	@FXML
	private TableView<CoinUserSale> tbTransaction;
	@FXML
	private TableColumn<CoinUserSale, Integer> colSaleId = new TableColumn<>("ID");
	@FXML
	private TableColumn<CoinUserSale, String> colSaleBuyer = new TableColumn<>("Comprador");
	@FXML
	private TableColumn<CoinUserSale, String> colSaleSeller = new TableColumn<>("Vendedor");
	@FXML
	private TableColumn<CoinUserSale, Float> colSalePrice = new TableColumn<>("Preço");
	@FXML
	private TableColumn<CoinUserSale, String> colSaleCoinName = new TableColumn<>("Moeda");
	@FXML
	private TableColumn<CoinUserSale, String> colSaleDate = new TableColumn<>("Data");

	public void initialize() {
		fixImage(profilePhoto, true);
		updateBoxes();
		checkInputs();
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
			stage.centerOnScreen();
		}
	}

	public void checkInputs() {
		Util.addTextListener("", 100, txtCoinName);
		Util.addTextListener("[^0-9]", 9, txtDenomination);
		Util.addTextListener("[^0-9,.]", 6, txtDiameter);
		Util.addTextListener("[^0-9,.]", 6, txtThickness);
		Util.addTextListener("[^0-9,.]", 6, txtWeight);
		Util.addTextListener("[^a-zA-Z]", 3, txtCountryCode);
		Util.addTextListener("[^a-z A-Z]", 60, txtCountryName);
		Util.addTextListener("[^a-z A-Z]", 30, txtShapeName);
		Util.addTextListener("[^a-z A-Z]", 30, txtMaterialName);
		Util.addTextListener("[^a-z A-Z]", 30, txtEdgeName);
		Util.addTextListener("[^a-z A-Z]", 50, txtFirstName);
		Util.addTextListener("[^a-z A-Z]", 50, txtLastName);
		Util.addTextListener("[^a-zA-Z]", 16, txtUsername);
		Util.addTextListener("", 32, txtPassword);
		Util.addTextListener("", 32, txtPasswordConfirmation);
		Util.addTextListener("", 100, txtEmail);
	}

	public boolean validateCoinFields() {
		if (txtCoinName.getText().isBlank() || txtDenomination.getText().isBlank() ||
				txtWeight.getText().isBlank() || txtDiameter.getText().isBlank() ||
				txtThickness.getText().isBlank() || boxRarity.getValue() == null ||
				boxCountry.getSelectionModel().getSelectedItem() == null ||
				boxShape.getCheckModel().getCheckedItems().isEmpty() ||
				boxMaterial.getCheckModel().getCheckedItems().isEmpty() ||
				boxEdge.getCheckModel().getCheckedItems().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique os campos.");
			alert.setContentText("Você precisa preencher todos os campos!");
			alert.showAndWait();
			return false;
		}
		try {
			Float validador = Float.parseFloat(txtWeight.getText().replace(",", "."));
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique o peso.");
			alert.setContentText("O valor digitado no peso não corresponde a um número!");
			alert.showAndWait();
			return false;
		}
		try {
			Float validador = Float.parseFloat(txtDiameter.getText().replace(",", "."));
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique o diametro.");
			alert.setContentText("O valor digitado no diametro não corresponde a um número!");
			alert.showAndWait();
			return false;
		}
		try {
			Float validador = Float.parseFloat(txtThickness.getText().replace(",", "."));
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique a grossura.");
			alert.setContentText("O valor digitado na grossura não corresponde a um número!");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public boolean checkPassword() {
		if (!Validator.passwordRequirements(txtPassword.getText(), txtPasswordConfirmation.getText(), lblWarning)) {
			return false;
		}
		return true;
	}

	public boolean validateCountryFields() {
		if (txtCountryCode.getText().isBlank() || txtCountryName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique os campos.");
			alert.setContentText("Você precisa preencher todos os campos!");
			alert.showAndWait();
			return false;
		} else {
			if (txtCountryCode.getText().length() < 2) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ERRO!");
				alert.setHeaderText("Verifique o código do país.");
				alert.setContentText("O código do país deve ter pelo menos 2 caracteres!");
				alert.showAndWait();
				return false;
			}
		}
		return true;
	}

	public boolean validateShapeFields() {
		if (txtShapeName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique os campos.");
			alert.setContentText("Vocé precisa preencher todos os campos!");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public boolean validateMaterialFields() {
		if (txtMaterialName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique os campos.");
			alert.setContentText("Vocé precisa preencher todos os campos!");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public boolean validateEdgeFields() {
		if (txtEdgeName.getText().isBlank()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ERRO!");
			alert.setHeaderText("Verifique os campos.");
			alert.setContentText("Vocé precisa preencher todos os campos!");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	public void registerCoin() {
		if (validateCoinFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de registro");
			alert.setContentText("Deseja realmente registrar esta moeda?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				Coin tempCoin = new Coin();
				tempCoin.setId(0);
				tempCoin.setName(txtCoinName.getText());
				tempCoin.setDenomination(Integer.parseInt(txtDenomination.getText()));
				tempCoin.setWeight(Float.parseFloat(txtWeight.getText().replace(",", ".")));
				tempCoin.setDiameter(Float.parseFloat(txtDiameter.getText().replace(",", ".")));
				tempCoin.setThickness(Float.parseFloat(txtThickness.getText().replace(",", ".")));
				tempCoin.setRarity(boxRarity.getValue());

				for (Country country : countryList) {
					if (boxCountry.getSelectionModel().getSelectedItem().equals(country.getName())) {
						tempCoin.setCountry(country);
						break;
					}
				}
				tempCoin.getShapes().clear();
				for (Shape shape : shapeList) {
					if (boxShape.getCheckModel().isChecked(shape.getName())) {
						tempCoin.getShapes().add(shape);
					}
				}
				tempCoin.getMaterials().clear();
				for (Material material : materialList) {
					if (boxMaterial.getCheckModel().isChecked(material.getName())) {
						tempCoin.getMaterials().add(material);
					}
				}
				tempCoin.getEdges().clear();
				for (Edge edge : edgeList) {
					if (boxEdge.getCheckModel().isChecked(edge.getName())) {
						tempCoin.getEdges().add(edge);
					}
				}

				if (coinService.save(tempCoin)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao registrar moeda");
					alertSuccess.setContentText("Moeda registrada com sucesso!");
					alertSuccess.showAndWait();

					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void registerCountry() {
		if (validateCountryFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de registro");
			alert.setContentText("Deseja realmente registrar este país?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				Country tempCountry = new Country();
				tempCountry.setId(0);
				tempCountry.setCode(txtCountryCode.getText());
				tempCountry.setName(txtCountryName.getText());

				if (countryService.save(tempCountry)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao registrar país");
					alertSuccess.setContentText("País registrado com sucesso!");
					alertSuccess.showAndWait();

					clearCountryFields();
					loadCountryTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void registerShape() {
		if (validateShapeFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de registro");
			alert.setContentText("Deseja realmente registrar este formato?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				Shape tempShape = new Shape();
				tempShape.setId(0);
				tempShape.setName(txtShapeName.getText());

				if (shapeService.save(tempShape)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao registrar formato");
					alertSuccess.setContentText("Formato registrado com sucesso!");
					alertSuccess.showAndWait();

					clearShapeFields();
					loadShapeTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void registerMaterial() {
		if (validateMaterialFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de registro");
			alert.setContentText("Deseja realmente registrar este material?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				Material tempMaterial = new Material();
				tempMaterial.setId(0);
				tempMaterial.setName(txtMaterialName.getText());

				if (materialService.save(tempMaterial)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao registrar material");
					alertSuccess.setContentText("Material registrado com sucesso!");
					alertSuccess.showAndWait();

					clearMaterialFields();
					loadMaterialTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void registerEdge() {
		if (validateEdgeFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de registro");
			alert.setContentText("Deseja realmente registrar esta borda?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				Edge tempEdge = new Edge();
				tempEdge.setId(0);
				tempEdge.setName(txtEdgeName.getText());

				if (edgeService.save(tempEdge)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao registrar borda");
					alertSuccess.setContentText("Borda registrada com sucesso!");
					alertSuccess.showAndWait();

					clearEdgeFields();
					loadEdgeTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void registerUser() {

	}

	public void deleteCoin() {
		if (tbCoin.getSelectionModel().getSelectedItem() != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de remoção");
			alert.setContentText(
					"Deseja realmente apagar a moeda: " + tbCoin.getSelectionModel().getSelectedItem().getName() + "?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				coin = tbCoin.getSelectionModel().getSelectedItem();
				if (coinService.delete(coin)) {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Sucesso!");
					alert.setHeaderText("Sucesso na remoção");
					alert.setContentText("Moeda apagada com sucesso!");
					alert.showAndWait();

					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void deleteCountry() {
		if (tbCountry.getSelectionModel().getSelectedItem() != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de remoção");
			alert.setContentText("Deseja realmente apagar o país: "
					+ tbCountry.getSelectionModel().getSelectedItem().getName() + "?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				country = tbCountry.getSelectionModel().getSelectedItem();
				if (countryService.delete(country)) {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Sucesso!");
					alert.setHeaderText("Sucesso na remoção");
					alert.setContentText("País apagado com sucesso!");
					alert.showAndWait();

					clearCountryFields();
					loadCountryTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void deleteShape() {
		if (tbShape.getSelectionModel().getSelectedItem() != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de remoção");
			alert.setContentText("Deseja realmente apagar o formato: "
					+ tbShape.getSelectionModel().getSelectedItem().getName() + "?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				shape = tbShape.getSelectionModel().getSelectedItem();
				if (shapeService.delete(shape)) {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Sucesso!");
					alert.setHeaderText("Sucesso na remoção");
					alert.setContentText("Formato apagado com sucesso!");
					alert.showAndWait();

					clearShapeFields();
					loadShapeTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}

		}
	}

	public void deleteMaterial() {
		if (tbMaterial.getSelectionModel().getSelectedItem() != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de remoção");
			alert.setContentText("Deseja realmente apagar o material: "
					+ tbMaterial.getSelectionModel().getSelectedItem().getName() + "?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				material = tbMaterial.getSelectionModel().getSelectedItem();
				if (materialService.delete(material)) {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Sucesso!");
					alert.setHeaderText("Sucesso na remoção");
					alert.setContentText("Material apagado com sucesso!");
					alert.showAndWait();

					clearMaterialFields();
					loadMaterialTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void deleteEdge() {
		if (tbEdge.getSelectionModel().getSelectedItem() != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de remoção");
			alert.setContentText("Deseja realmente apagar a borda: "
					+ tbEdge.getSelectionModel().getSelectedItem().getName() + "?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				edge = tbEdge.getSelectionModel().getSelectedItem();
				if (edgeService.delete(edge)) {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Sucesso!");
					alert.setHeaderText("Sucesso na remoção");
					alert.setContentText("Borda apagada com sucesso!");
					alert.showAndWait();

					clearEdgeFields();
					loadEdgeTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void deleteUser() {

	}

	public void updateCoin() {
		if (validateCoinFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de atualização");
			alert.setContentText("Deseja realmente atualizar este moeda?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				coin.setName(txtCoinName.getText());
				coin.setDenomination(Integer.parseInt(txtDenomination.getText()));
				coin.setWeight(Float.parseFloat(txtWeight.getText().replace(",", ".")));
				coin.setDiameter(Float.parseFloat(txtDiameter.getText().replace(",", ".")));
				coin.setThickness(Float.parseFloat(txtThickness.getText().replace(",", ".")));
				coin.setRarity(boxRarity.getValue());

				for (Country country : countryList) {
					if (boxCountry.getSelectionModel().getSelectedItem().equals(country.getName())) {
						coin.setCountry(country);
						break;
					}
				}
				coin.getShapes().clear();
				for (Shape shape : shapeList) {
					if (boxShape.getCheckModel().isChecked(shape.getName())) {
						coin.getShapes().add(shape);
					}
				}
				coin.getMaterials().clear();
				for (Material material : materialList) {
					if (boxMaterial.getCheckModel().isChecked(material.getName())) {
						coin.getMaterials().add(material);
					}
				}
				coin.getEdges().clear();
				for (Edge edge : edgeList) {
					if (boxEdge.getCheckModel().isChecked(edge.getName())) {
						coin.getEdges().add(edge);
					}
				}

				if (coinService.save(coin)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao atualizar moeda");
					alertSuccess.setContentText("Moeda atualizada com sucesso!");
					alertSuccess.showAndWait();

					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void updateCountry() {
		if (validateCountryFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de atualização");
			alert.setContentText("Deseja realmente atualizar este país?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				country.setCode(txtCountryCode.getText());
				country.setName(txtCountryName.getText());

				if (countryService.save(country)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao atualizar país");
					alertSuccess.setContentText("País atualizado com sucesso!");
					alertSuccess.showAndWait();

					clearCountryFields();
					loadCountryTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void updateShape() {
		if (validateShapeFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de atualização");
			alert.setContentText("Deseja realmente atualizar este formato?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				shape.setName(txtShapeName.getText());

				if (shapeService.save(shape)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao atualizar formato");
					alertSuccess.setContentText("Formato atualizado com sucesso!");
					alertSuccess.showAndWait();

					clearShapeFields();
					loadShapeTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void updateMaterial() {
		if (validateMaterialFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de atualização");
			alert.setContentText("Deseja realmente atualizar este material?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				material.setName(txtMaterialName.getText());

				if (materialService.save(material)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao atualizar material");
					alertSuccess.setContentText("Material atualizado com sucesso!");
					alertSuccess.showAndWait();

					clearMaterialFields();
					loadMaterialTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void updateEdge() {
		if (validateEdgeFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de atualização");
			alert.setContentText("Deseja realmente atualizar esta borda?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				edge.setName(txtEdgeName.getText());

				if (edgeService.save(edge)) {
					Alert alertSuccess = new Alert(AlertType.INFORMATION);
					alertSuccess.setTitle("Sucesso");
					alertSuccess.setHeaderText("Sucesso ao atualizar borda");
					alertSuccess.setContentText("Borda atualizada com sucesso!");
					alertSuccess.showAndWait();

					clearEdgeFields();
					loadEdgeTable();

					updateBoxes();
					clearCoinFields();
					loadCoinTable();
				}
			}
		}
	}

	public void updateUser() {

	}

	public void loadSelectedCoin() {
		coin = tbCoin.getSelectionModel().getSelectedItem();

		if (coin != null) {
			txtCoinName.setText(coin.getName());
			txtDenomination.setText(Integer.toString(coin.getDenomination()));
			txtWeight.setText(coin.getWeight().toString());
			txtDiameter.setText(coin.getDiameter().toString());
			txtThickness.setText(coin.getThickness().toString());
			boxRarity.setValue(coin.getRarity());
			boxCountry.getSelectionModel().select(coin.getCountry().getName());
			boxShape.getCheckModel().clearChecks();
			boxMaterial.getCheckModel().clearChecks();
			boxEdge.getCheckModel().clearChecks();
			for (Shape shape : coin.getShapes()) {
				boxShape.getCheckModel().check(shape.getName());
			}
			for (Material material : coin.getMaterials()) {
				boxMaterial.getCheckModel().check(material.getName());
			}
			for (Edge edge : coin.getEdges()) {
				boxEdge.getCheckModel().check(edge.getName());
			}
		}
	}

	public void loadSelectedCountry() {
		country = tbCountry.getSelectionModel().getSelectedItem();

		if (country != null) {
			txtCountryCode.setText(country.getCode());
			txtCountryName.setText(country.getName());
		}
	}

	public void loadSelectedShape() {
		shape = tbShape.getSelectionModel().getSelectedItem();

		if (shape != null) {
			txtShapeName.setText(shape.getName());
		}
	}

	public void loadSelectedMaterial() {
		material = tbMaterial.getSelectionModel().getSelectedItem();

		if (material != null) {
			txtMaterialName.setText(material.getName());
		}
	}

	public void loadSelectedEdge() {
		edge = tbEdge.getSelectionModel().getSelectedItem();

		if (edge != null) {
			txtEdgeName.setText(edge.getName());
		}
	}

	public void loadSelectedUser() {

	}

	public void loadSelectedRequest() throws IOException {
		request = tbRequest.getSelectionModel().getSelectedItem();

		if(request != null) {
			txtRequestUser.setText(request.getUser().getFirstName() + " " + request.getUser().getLastName());
			txtRequestItem.setText(request.getRequest().getRequestedItem().toString());
			txtRequestNotes.setText(request.getRequest().getNotes());
		}
	}

	public void loadCoinTable() {
		tbCoin.getItems().clear();
		tbCoin.getColumns().clear();
		Font font = new Font(20);

		coinService = new CoinService();
		for (Coin coin : coinService.findAll()) {
			obsCoinList.add(coin);
		}

		colCoinName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
		colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		colWeight.setCellFactory(column -> new TableCell<Coin, Float>() {
			@Override
			protected void updateItem(Float item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText("");
				} else {
					setText(String.format("%.1f", item));
				}
				TextFieldTableCell<?, ?> cell = new TextFieldTableCell<>();
            	cell.setFont(font);
			}
		});
		colDiameter.setCellValueFactory(new PropertyValueFactory<>("diameter"));
		colDiameter.setCellFactory(column -> new TableCell<Coin, Float>() {
			@Override
			protected void updateItem(Float item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText("");
				} else {
					setText(String.format("%.1f", item));
				}
			}
		});
		colThickness.setCellValueFactory(new PropertyValueFactory<>("thickness"));
		colThickness.setCellFactory(column -> new TableCell<Coin, Float>() {
			@Override
			protected void updateItem(Float item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText("");
				} else {
					setText(String.format("%.1f", item));
				}
			}
		});
		colRarity.setCellValueFactory(new PropertyValueFactory<>("rarity"));
		colCountry
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry().getName()));
		colShape.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Coin, List<String>>, ObservableValue<List<String>>>() {
					@Override
					public ObservableValue<List<String>> call(
							TableColumn.CellDataFeatures<Coin, List<String>> cellData) {
						List<Shape> shapes = cellData.getValue().getShapes();
						if (shapes != null && !shapes.isEmpty()) {
							List<String> shapeNames = new ArrayList<>();
							for (Shape shape : shapes) {
								shapeNames.add(shape.getName());
							}
							return new SimpleObjectProperty<>(shapeNames);
						} else {
							return new SimpleObjectProperty<>(new ArrayList<>());
						}
					}
				});
		colMaterial.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Coin, List<String>>, ObservableValue<List<String>>>() {
					@Override
					public ObservableValue<List<String>> call(
							TableColumn.CellDataFeatures<Coin, List<String>> cellData) {
						List<Material> materials = cellData.getValue().getMaterials();
						if (materials != null && !materials.isEmpty()) {
							List<String> materialNames = new ArrayList<>();
							for (Material material : materials) {
								materialNames.add(material.getName());
							}
							return new SimpleObjectProperty<>(materialNames);
						} else {
							return new SimpleObjectProperty<>(new ArrayList<>());
						}
					}
				});
		colEdge.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Coin, List<String>>, ObservableValue<List<String>>>() {
					@Override
					public ObservableValue<List<String>> call(
							TableColumn.CellDataFeatures<Coin, List<String>> cellData) {
						List<Edge> edges = cellData.getValue().getEdges();
						if (edges != null && !edges.isEmpty()) {
							List<String> edgeNames = new ArrayList<>();
							for (Edge edge : edges) {
								edgeNames.add(edge.getName());
							}
							return new SimpleObjectProperty<>(edgeNames);
						} else {
							return new SimpleObjectProperty<>(new ArrayList<>());
						}
					}
				});
		tbCoin.getColumns().addAll(colCoinName, colDenomination, colWeight, colDiameter, colThickness, colRarity,
				colCountry, colShape, colMaterial, colEdge);
		tbCoin.setItems(obsCoinList);
	}

	public void loadCountryTable() {
		tbCountry.getItems().clear();
		tbCountry.getColumns().clear();

		countryService = new CountryService();
		for (Country country : countryService.findAll()) {
			obsCountryList.add(country);
		}

		colCountryCode.setCellValueFactory(new PropertyValueFactory<>("code"));
		colCountryName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbCountry.getColumns().addAll(colCountryCode, colCountryName);
		tbCountry.setItems(obsCountryList);
	}

	public void loadShapeTable() {
		tbShape.getItems().clear();
		tbShape.getColumns().clear();

		shapeService = new ShapeService();
		for (Shape shape : shapeService.findAll()) {
			obsShapeList.add(shape);
		}

		colShapeName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbShape.getColumns().addAll(colShapeName);
		tbShape.setItems(obsShapeList);
	}

	public void loadMaterialTable() {
		tbMaterial.getItems().clear();
		tbMaterial.getColumns().clear();

		materialService = new MaterialService();
		for (Material material : materialService.findAll()) {
			obsMaterialList.add(material);
		}

		colMaterialName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbMaterial.getColumns().addAll(colMaterialName);
		tbMaterial.setItems(obsMaterialList);
	}

	public void loadEdgeTable() {
		tbEdge.getItems().clear();
		tbEdge.getColumns().clear();

		edgeService = new EdgeService();
		for (Edge edge : edgeService.findAll()) {
			obsEdgeList.add(edge);
		}

		colEdgeName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tbEdge.getColumns().addAll(colEdgeName);
		tbEdge.setItems(obsEdgeList);
	}

	public void loadUserTable() {

	}

	public void loadRequestTable(){
		tbRequest.getItems().clear();
		tbRequest.getColumns().clear();

		userRequestService = new UserRequestService();
		for(UserRequest userRequest : userRequestService.findAll()){
			obsRequestList.add(userRequest);
		}

		colRequestingPerson.setCellValueFactory(cellData -> new SimpleStringProperty(
			cellData.getValue().getUser().getFirstName() + " " + cellData.getValue().getUser().getLastName()));
		colRequestedItem.setCellValueFactory(cellData -> new SimpleStringProperty(
			cellData.getValue().getRequest().getRequestedItem().toString()));
		colRequestNotes.setCellValueFactory(cellData -> new SimpleStringProperty(
			cellData.getValue().getRequest().getNotes()));
		colRequestSituation.setCellValueFactory(cellData -> new SimpleStringProperty(
			cellData.getValue().getRequest().getRequestSituation().toString()));
		colRequestDate.setCellValueFactory(cellData -> new SimpleStringProperty(
			Util.localDateTimeFormatter(cellData.getValue().getRequest().getRequestDate())));
		colCloseRequestDate.setCellFactory(cellData -> new TableCell<UserRequest, LocalDateTime>() {
			@Override
			protected void updateItem(LocalDateTime item, boolean empty){
				super.updateItem(item, empty);
				if(getTableRow().getItem() == null){
					setText(null);
				} else {
					item = getTableRow().getItem().getRequest().getCloseRequestDate();
					setText(empty || item == null ? "Não possui" : Util.localDateTimeFormatter(item));
				}
			}
		});

		tbRequest.getColumns().addAll(colRequestingPerson, colRequestedItem, colRequestSituation, colRequestNotes, colRequestDate, colCloseRequestDate);
		tbRequest.setItems(obsRequestList);
	}

	public void loadTransactionTable(){
		tbTransaction.getItems().clear();
		tbTransaction.getColumns().clear();

		coinUserSaleService = new CoinUserSaleService();
		for(CoinUserSale coinUserSale : coinUserSaleService.findAll()){
			obsCoinUserSaleList.add(coinUserSale);
		}

		colSaleId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colSaleBuyer.setCellValueFactory(cellData -> new SimpleStringProperty(
			cellData.getValue().getSale().getBuyer().getFirstName() + " " + cellData.getValue().getSale().getBuyer().getLastName()));
		colSaleSeller.setCellValueFactory(cellData -> new SimpleStringProperty(
			cellData.getValue().getSale().getSeller().getFirstName() + " " + cellData.getValue().getSale().getSeller().getLastName()));
		colSalePrice.setCellValueFactory(cellData -> new SimpleObjectProperty<Float>(
			cellData.getValue().getSale().getPrice()));
		colSalePrice.setCellFactory(column -> new TableCell<CoinUserSale, Float>() {
			@Override
			protected void updateItem(Float item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : String.format("R$%.2f", item));
			}
		});
		colSaleCoinName.setCellValueFactory(cellData -> new SimpleStringProperty(
			cellData.getValue().getCoinUser().getCoin().getName()));
		colSaleDate.setCellValueFactory(cellData -> new SimpleStringProperty(
			Util.localDateTimeFormatter(cellData.getValue().getSale().getSaleDate())));
		
		tbTransaction.getColumns().addAll(colSaleId, colSaleBuyer, colSaleSeller, colSalePrice, colSaleCoinName, colSaleDate);
		tbTransaction.setItems(obsCoinUserSaleList);
	}

	public void searchCoin() {
		if (txtCoinSearch.getText().isBlank()) {
			loadCoinTable();
		} else {
			loadCoinTable();
			ObservableList<Coin> tempObsCoinList = FXCollections.observableArrayList();
			for (Coin coin : obsCoinList) {
				if (coin.getName().toLowerCase().contains(txtCoinSearch.getText().toLowerCase()) ||
						coin.getRarity().toString().toLowerCase().contains(txtCoinSearch.getText().toLowerCase()) ||
						coin.getCountry().getName().toLowerCase().contains(txtCoinSearch.getText().toLowerCase())) {
					tempObsCoinList.add(coin);
				}
				for (Shape shape : coin.getShapes()) {
					if (shape.getName().toLowerCase().contains(txtCoinSearch.getText().toLowerCase())) {
						if (!tempObsCoinList.contains(coin)) {
							tempObsCoinList.add(coin);
						}
					}
				}
				for (Material material : coin.getMaterials()) {
					if (material.getName().toLowerCase().contains(txtCoinSearch.getText().toLowerCase())) {
						if (!tempObsCoinList.contains(coin)) {
							tempObsCoinList.add(coin);
						}
					}
				}
				for (Edge edge : coin.getEdges()) {
					if (edge.getName().toLowerCase().contains(txtCoinSearch.getText().toLowerCase())) {
						if (!tempObsCoinList.contains(coin)) {
							tempObsCoinList.add(coin);
						}
					}
				}
			}

			tbCoin.getItems().clear();
			tbCoin.setItems(tempObsCoinList);
		}
	}

	public void searchCountry() {
		if (txtCountrySearch.getText().isBlank()) {
			loadCountryTable();
		} else {
			loadCountryTable();
			ObservableList<Country> tempObsCountryList = FXCollections.observableArrayList();
			for (Country country : obsCountryList) {
				if (country.getName().toLowerCase().contains(txtCountrySearch.getText().toLowerCase()) ||
						country.getCode().toLowerCase().contains(txtCountrySearch.getText().toLowerCase())) {
					tempObsCountryList.add(country);
				}
			}
			tbCountry.getItems().clear();
			tbCountry.setItems(tempObsCountryList);
		}
	}

	public void searchShape() {
		if (txtShapeSearch.getText().isBlank()) {
			loadShapeTable();
		} else {
			loadShapeTable();
			ObservableList<Shape> tempObsShapeList = FXCollections.observableArrayList();
			for (Shape shape : obsShapeList) {
				if (shape.getName().toLowerCase().contains(txtShapeSearch.getText().toLowerCase())) {
					tempObsShapeList.add(shape);
				}
			}
			tbShape.getItems().clear();
			tbShape.setItems(tempObsShapeList);
		}
	}

	public void searchMaterial() {
		if (txtMaterialSearch.getText().isBlank()) {
			loadMaterialTable();
		} else {
			loadMaterialTable();
			ObservableList<Material> tempObsMaterialList = FXCollections.observableArrayList();
			for (Material material : obsMaterialList) {
				if (material.getName().toLowerCase().contains(txtMaterialSearch.getText().toLowerCase())) {
					tempObsMaterialList.add(material);
				}
			}
			tbMaterial.getItems().clear();
			tbMaterial.setItems(tempObsMaterialList);
		}
	}

	public void searchEdge() {
		if (txtEdgeSearch.getText().isBlank()) {
			loadEdgeTable();
		} else {
			loadEdgeTable();
			ObservableList<Edge> tempObsEdgeList = FXCollections.observableArrayList();
			for (Edge edge : obsEdgeList) {
				if (edge.getName().toLowerCase().contains(txtEdgeSearch.getText().toLowerCase())) {
					tempObsEdgeList.add(edge);
				}
			}
			tbEdge.getItems().clear();
			tbEdge.setItems(tempObsEdgeList);
		}
	}

	public void searchUser() {

	}

	public void searchRequest(){
		if (txtRequestSearch.getText().isBlank()) {
			loadRequestTable();
		} else {
			loadRequestTable();
			ObservableList<UserRequest> tempObsRequestList = FXCollections.observableArrayList();
			for (UserRequest request : obsRequestList) {
				if (request.getRequest().getNotes().toLowerCase().contains(txtRequestSearch.getText().toLowerCase()) ||
				request.getRequest().getRequestedItem().toString().toLowerCase().contains(txtRequestSearch.getText().toLowerCase()) ||
				request.getRequest().getRequestSituation().toString().toLowerCase().contains(txtRequestSearch.getText().toLowerCase())) {
					tempObsRequestList.add(request);
				}
			}
			
			tbRequest.getItems().clear();
			tbRequest.setItems(tempObsRequestList);
		}
	}

	public void clearCoinFields() {
		coin = new Coin();
		txtCoinName.clear();
		txtDenomination.clear();
		txtWeight.clear();
		txtDiameter.clear();
		txtThickness.clear();
		boxRarity.getSelectionModel().clearSelection();
		boxCountry.getSelectionModel().clearSelection();
		boxShape.getCheckModel().clearChecks();
		boxMaterial.getCheckModel().clearChecks();
		boxEdge.getCheckModel().clearChecks();
		tbCoin.getSelectionModel().clearSelection();
	}

	public void clearCountryFields() {
		country = new Country();
		txtCountryCode.clear();
		txtCountryName.clear();
		tbCountry.getSelectionModel().clearSelection();
	}

	public void clearShapeFields() {
		shape = new Shape();
		txtShapeName.clear();
		tbShape.getSelectionModel().clearSelection();
	}

	public void clearMaterialFields() {
		material = new Material();
		txtMaterialName.clear();
		tbMaterial.getSelectionModel().clearSelection();
	}

	public void clearEdgeFields() {
		edge = new Edge();
		txtEdgeName.clear();
		tbEdge.getSelectionModel().clearSelection();
	}

	public void clearUserFields() {
		user = new User();
		txtFirstName.clear();
		txtLastName.clear();
		txtBirthDate.setValue(null);
		txtCpf.clear();
		boxGender.getSelectionModel().select(-1);
		txtUsername.clear();
		txtPassword.clear();
		txtPasswordConfirmation.clear();
		txtEmail.clear();
		lblWarning.setText("");
		lblSelectedFile.setText("");
		// tbUser.getSelectionModel().clearSelection();
	}

	public void clearRequestFields() {
		request = new UserRequest();
		txtRequestUser.clear();
		txtRequestItem.clear();
		txtRequestNotes.clear();
		tbRequest.getSelectionModel().clearSelection();
	}

	public void acceptRequest() {

	}

	public void rejectRequest() {

	}

	public void updateBoxes() {
		boxGender.getItems().clear();
		boxRarity.getItems().clear();
		boxCountry.getItems().clear();

		if (boxShape != null) {
			boxShape.getItems().clear();
		}
		if (boxMaterial != null) {
			boxMaterial.getItems().clear();
		}
		if (boxEdge != null) {
			boxEdge.getItems().clear();
		}

		shapeList = shapeService.findAll();
		materialList = materialService.findAll();
		edgeList = edgeService.findAll();

		ObservableList<String> shapes = FXCollections.observableArrayList();
		ObservableList<String> materials = FXCollections.observableArrayList();
		ObservableList<String> edges = FXCollections.observableArrayList();

		for (Shape shape : shapeList) {
			shapes.add(shape.getName());
		}
		for (Material material : materialList) {
			materials.add(material.getName());
		}
		for (Edge edge : edgeList) {
			edges.add(edge.getName());
		}

		boxRarity.getItems().addAll(Rarity.values());
		boxCountry.setItems(loadCountries());
		boxGender.getItems().addAll(Gender.values());

		boxShape = new NumismasterCheckComboBox<String>(shapes, 150, 30, 225, 375);
		paneCoin.getChildren().add(boxShape);

		boxMaterial = new NumismasterCheckComboBox<String>(materials, 150, 30, 25, 450);
		paneCoin.getChildren().add(boxMaterial);

		boxEdge = new NumismasterCheckComboBox<String>(edges, 150, 30, 225, 450);
		paneCoin.getChildren().add(boxEdge);

		boxListener(boxShape);
		boxListener(boxMaterial);
		boxListener(boxEdge);
	}

	private void boxListener(NumismasterCheckComboBox<String> box) {
		box.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) {
				int i = box.getCheckModel().getCheckedIndices().size();
				if (i > 3) {
					box.getCheckModel().clearChecks();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ERRO!");
					alert.setHeaderText("Vamos com calma!");
					alert.setContentText("Você só pode selecionar até 3 itens!");
					alert.showAndWait();
				} else {
					while (c.next()) {
						box.setTitle("Selecionados: " + i + "/3");
					}
				}
			}
		});
	}

	public void chooseFile() {

	}

	@FXML
	private void checkCpf() {
		MaskTextField mtf = new MaskTextField();
		mtf.setMask("###.###.###-##");
		mtf.setValidCharacters("0123456789");
		mtf.setTf(txtCpf);
		mtf.formatter();
	}

	public ObservableList<String> loadCountries() {
		countryList = countryService.findAll();
		final ObservableList<String> obsList = FXCollections.observableArrayList();
		for (Country c : countryList) {
			obsList.add(c.getName());
		}

		return obsList;
	}

	public ObservableList<String> loadEdges() {
		final ObservableList<String> obsList = FXCollections.observableArrayList();
		for (Edge e : edgeList) {
			obsList.add(e.getName());
		}

		return obsList;
	}

	public void loadUser(User newUser) {
		user = newUser;
		lblName.setText(user.getFirstName() + " " + user.getLastName());
		lblName.setTextFill(Color.rgb(255, 85, 85));
		try {
			profilePhoto.setImage(new Image(Util.convertFromBlob(user.getProfilePhoto())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void changeToUserMenu(ActionEvent e) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/numismaster/view/MainMenu.fxml"));
		root = loader.load();
		MainMenuController mmc = loader.getController();
		mmc.loadUser(user);
		stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.getIcons().add(new Image("/com/numismaster/icon/large-app-icon.png"));
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
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
		alert.setContentText("Tem certeza que deseja encerrar o sistema?");

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