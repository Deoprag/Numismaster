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
import com.numismaster.repository.CoinRepository;
import com.numismaster.repository.CountryRepository;
import com.numismaster.repository.EdgeRepository;
import com.numismaster.repository.MaterialRepository;
import com.numismaster.repository.ShapeRepository;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;

public class RegisterItensController {

	private User user;

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
	@FXML
	private TextField txtName;
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
	private ComboBox<String> boxEdge;
	@FXML
	private NumismasterCheckComboBox<String> boxShape;
	@FXML
	private NumismasterCheckComboBox<String> boxMaterial;
	@FXML
	private TableView<Coin> tbCoin;
	@FXML
	private TableColumn<Coin, String> colName = new TableColumn<>("Nome");
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
	private TableColumn<Coin, String> colEdge = new TableColumn<>("Borda");
	@FXML
	private Pane paneCoin;

	private double x, y = 0;

	public void initialize() {
		fixImage(profilePhoto, true);
		initializeBoxes();
		loadTable();
	}

	public void loadTable() {
		CoinRepository cr = new CoinRepository();
		ObservableList<Coin> coinList = FXCollections.observableArrayList();

		for (Coin coin : cr.findAll()) {
			coinList.add(coin);
		}

		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
		colWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
		colDiameter.setCellValueFactory(new PropertyValueFactory<>("diameter"));
		colThickness.setCellValueFactory(new PropertyValueFactory<>("thickness"));
		colRarity.setCellValueFactory(new PropertyValueFactory<>("rarity"));
		colCountry.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry().getName()));
		colShape.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, List<String>>, ObservableValue<List<String>>>() {
			@Override
			public ObservableValue<List<String>> call(TableColumn.CellDataFeatures<Coin, List<String>> cellData) {
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
		colMaterial.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Coin, List<String>>, ObservableValue<List<String>>>() {
			@Override
			public ObservableValue<List<String>> call(TableColumn.CellDataFeatures<Coin, List<String>> cellData) {
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
		colEdge.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEdge().getName()));

		tbCoin.getColumns().addAll(colName, colDenomination, colWeight, colDiameter, colThickness, colRarity, colCountry, colShape, colMaterial, colEdge);
		tbCoin.setItems(coinList);
	}

	public void initializeBoxes() {

		ShapeRepository sr = new ShapeRepository();
		MaterialRepository mr = new MaterialRepository();

		List<Shape> shapeList = sr.findAll();
		List<Material> materialList = mr.findAll();

		ObservableList<String> shapes = FXCollections.observableArrayList();
		ObservableList<String> materials = FXCollections.observableArrayList();

		for (Shape shape : shapeList) {
			shapes.add(shape.getName());
		}
		for (Material material : materialList) {
			materials.add(material.getName());
		}

		boxRarity.getItems().addAll(Rarity.values());
		boxCountry.setItems(loadCountries());
		boxEdge.setItems(loadEdges());

		boxShape = new NumismasterCheckComboBox<String>(shapes, 150, 30, 225, 375);
		boxShape.setTitle("Selecionados: 0/3");
		paneCoin.getChildren().add(boxShape);

		boxMaterial = new NumismasterCheckComboBox<String>(materials, 150, 30, 25, 450);
		boxMaterial.setTitle("Selecionados: 0/3");
		paneCoin.getChildren().add(boxMaterial);

		boxShape.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) {
				int i = boxShape.getCheckModel().getCheckedIndices().size();
				if (i > 3) {
					boxShape.getCheckModel().clearChecks();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ERRO!");
					alert.setHeaderText("Vamos com calma!");
					alert.setContentText("Você só pode selecionar até 3 itens!");
					alert.showAndWait();
				} else {
					while (c.next()) {
						boxShape.setTitle("Selecionados: " + i + "/3");
					}
				}
			}
		});

		boxMaterial.getCheckModel().getCheckedItems().addListener(new ListChangeListener<String>() {
			public void onChanged(ListChangeListener.Change<? extends String> c) {
				int i = boxMaterial.getCheckModel().getCheckedIndices().size();
				if (i > 3) {
					boxMaterial.getCheckModel().clearChecks();
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ERRO!");
					alert.setHeaderText("Vamos com calma!");
					alert.setContentText("Você só pode selecionar até 3 itens!");
					alert.showAndWait();
				} else {
					while (c.next()) {
						boxMaterial.setTitle("Selecionados: " + i + "/3");
					}
				}
			}
		});
	}

	public ObservableList<String> loadCountries() {
		CountryRepository cr = new CountryRepository();
		List<Country> list = cr.findAll();
		final ObservableList<String> obsList = FXCollections.observableArrayList();
		for (Country c : list) {
			obsList.add(c.getName());
		}

		return obsList;
	}

	public ObservableList<String> loadEdges() {
		EdgeRepository er = new EdgeRepository();
		List<Edge> list = er.findAll();
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
