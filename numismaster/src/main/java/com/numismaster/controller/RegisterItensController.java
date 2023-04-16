package com.numismaster.controller;

import java.util.ArrayList;
import java.util.List;

import com.numismaster.javafx.NumismasterCheckComboBox;
import com.numismaster.model.Coin;
import com.numismaster.model.Country;
import com.numismaster.model.Edge;
import com.numismaster.model.Material;
import com.numismaster.model.Rarity;
import com.numismaster.model.Shape;
import com.numismaster.model.User;
import com.numismaster.service.CoinService;
import com.numismaster.service.CountryService;
import com.numismaster.service.EdgeService;
import com.numismaster.service.MaterialService;
import com.numismaster.service.ShapeService;
import com.numismaster.util.Util;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RegisterItensController {

	private User user;
	private List<Shape> shapeList;
	private List<Edge> edgeList;
	private List<Material> materialList;
	private List<Country> countryList;
	private Coin coin;
	private double x, y = 0;

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
	
	//	Coin
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
	private TableColumn<Coin, Integer> colDenomination = new TableColumn<>("Valor Nominal");
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

	//	Country
	@FXML
	private TableView<Country> tbCountry;
	@FXML
	private Button btnRegisterCountry;
	@FXML
	private Button btnDeleteCountry;
	@FXML
	private Button btnUpdateCountry;
	
	//	Shape
	@FXML
	private TableView<Shape> tbShape;
	@FXML
	private Button btnRegisterShape;
	@FXML
	private Button btnDeleteShape;
	@FXML
	private Button btnUpdateShape;
	
	//	Material
	@FXML
	private TableView<Material> tbMaterial;
	@FXML
	private Button btnRegisterMaterial;
	@FXML
	private Button btnDeleteMaterial;
	@FXML
	private Button btnUpdateMaterial;
	
	//	Edge
	@FXML
	private TableView<Edge> tbEdge;
	@FXML
	private Button btnRegisterEdge;
	@FXML
	private Button btnDeleteEdge;
	@FXML
	private Button btnUpdateEdge;

	public void initialize() {
		fixImage(profilePhoto, true);
		initializeBoxes();
		loadCoinTable();
		checkInputs();
	}

	public void checkInputs() {
		txtDenomination.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^0-9]", "");
			if (!newValue.equals(filteredValue)) {
				txtDenomination.setText(filteredValue);
			}
		});
		txtDiameter.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^0-9,.]", "");
			if (!newValue.equals(filteredValue)) {
				txtDiameter.setText(filteredValue);
			}
		});
		txtThickness.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^0-9,.]", "");
			if (!newValue.equals(filteredValue)) {
				txtThickness.setText(filteredValue);
			}
		});
		txtWeight.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^0-9,.]", "");
			if (!newValue.equals(filteredValue)) {
				txtWeight.setText(filteredValue);
			}
		});
	}

	public boolean validadeCoinFields() {
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
		return true;
	}

	public boolean validateCountryFields(){

		return true;
	}

	public boolean validateShapeFields(){

		return true;
	}

	public boolean validadeMaterialFields(){

		return true;
	}

	public boolean validadeEdgeFields(){

		return true;
	}

	public void registerCoin() {
		if (validadeCoinFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de registro");
			alert.setContentText("Deseja realmente registrar este moeda?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				CoinService coinService = new CoinService();

				coin.setId(0);
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
					alertSuccess.setHeaderText("Sucesso ao registrar moeda");
					alertSuccess.setContentText("Moeda registrada com sucesso!");
					alertSuccess.showAndWait();

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

					loadCoinTable();
				}
			}
		}
	}

	public void registerCountry(){

	}
	
	public void registerShape(){

	}

	public void registerMaterial(){

	}

	public void registerEdge(){

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
				CoinService coinService = new CoinService();
				if (coinService.delete(coin)) {
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Sucesso!");
					alert.setHeaderText("Sucesso na remoção");
					alert.setContentText("Moeda apagada com sucesso!");
					loadCoinTable();
				}
			}
		}
	}

	public void deleteCountry(){

	}

	public void deleteShape(){

	}

	public void deleteMaterial(){

	}

	public void deleteEdge(){

	}

	public void updateCoin() {
		if (validadeCoinFields()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de atualização");
			alert.setContentText("Deseja realmente atualizar este moeda?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				CoinService coinService = new CoinService();

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
					
					loadCoinTable();
				}
			}
		}
	}

	public void updateCountry(){

	}

	public void updateShape(){

	}

	public void updateMaterial(){

	}

	public void updateEdge(){

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

	public void loadSelectedCountry(){

	}

	public void loadSelectedShape(){

	}

	public void loadSelectedMaterial(){

	}

	public void loadSelectedEdge(){

	}

	public void loadCoinTable() {
		tbCoin.getColumns().clear();
		CoinService coinService = new CoinService();
		ObservableList<Coin> coinList = FXCollections.observableArrayList();

		for (Coin coin : coinService.findAll()) {
			coinList.add(coin);
		}

		colCoinName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
		colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		colDiameter.setCellValueFactory(new PropertyValueFactory<>("diameter"));
		colThickness.setCellValueFactory(new PropertyValueFactory<>("thickness"));
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
		tbCoin.setItems(coinList);
	}

	public void loadCountryTable(){

	}

	public void loadShapeTable(){

	}

	public void loadMaterialTable(){

	}

	public void loadEdgeTable(){

	}

	public void searchCoin(){

	}

	public void searchCountry(){
		
	}

	public void searchShape(){

	}

	public void searchMaterial(){

	}

	public void searchEdge(){

	}

	public void initializeBoxes() {

		ShapeService shapeService = new ShapeService();
		MaterialService materialService = new MaterialService();
		EdgeService edgeService = new EdgeService();

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

	public ObservableList<String> loadCountries() {
		CountryService countryService = new CountryService();
		countryList = countryService.findAll();
		final ObservableList<String> obsList = FXCollections.observableArrayList();
		for (Country c : countryList) {
			obsList.add(c.getName());
		}

		return obsList;
	}

	public ObservableList<String> loadEdges() {
		EdgeService edgeService = new EdgeService();
		List<Edge> list = edgeService.findAll();
		final ObservableList<String> obsList = FXCollections.observableArrayList();
		for (Edge e : list) {
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
