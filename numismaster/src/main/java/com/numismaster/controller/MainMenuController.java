package com.numismaster.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import com.numismaster.javafx.MaskTextField;
import com.numismaster.model.Coin;
import com.numismaster.model.CoinUser;
import com.numismaster.model.Edge;
import com.numismaster.model.Gender;
import com.numismaster.model.Material;
import com.numismaster.model.Rarity;
import com.numismaster.model.Shape;
import com.numismaster.model.User;
import com.numismaster.service.CoinService;
import com.numismaster.service.CoinUserService;
import com.numismaster.service.UserService;
import com.numismaster.util.Util;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import lombok.Setter;

@Setter
public class MainMenuController {

	private int clickCount;
	private Coin selectedItem;
	private Coin lastSelectedItem;

	private Stage stage;
	private Stage stageRegisterCoin;
	private Scene scene;
	private Parent root;
	private User user;

	private CoinService coinService = new CoinService();
	private CoinUserService coinUserService = new CoinUserService();

	ObservableList<Coin> obsCoinList = FXCollections.observableArrayList();
	ObservableList<CoinUser> obsCoinUserList = FXCollections.observableArrayList();
	ObservableList<CoinUser> obsCoinUserListMkt = FXCollections.observableArrayList();

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
	private TextField txtCoinSearch;
	@FXML
	private TextField txtCoinUserSearch;
	@FXML
	private Label lblName;

	// Profile
	@FXML
	private ImageView editProfilePhoto;
	@FXML
	private TextField txtEditFirstName;
	@FXML
	private TextField txtEditLastName;
	@FXML
	private DatePicker txtEditBirthDate;
	@FXML
	private TextField txtEditCpf;
	@FXML
	private ChoiceBox<Gender> boxEditGender;
	@FXML
	private TextField txtEditUsername;
	@FXML
	private TextField txtEditEmail;
	@FXML
	private Button btnFileChooser;
	@FXML
	private Label lblSelectedFile;

	// MyCollection
	@FXML
	private TableView<CoinUser> tbCoinUser;
	@FXML
	private TableColumn<CoinUser, Blob> colImgFront = new TableColumn<>("Imagem Frontal");
	@FXML
	private TableColumn<CoinUser, Blob> colImgBack = new TableColumn<>("Imagem Traseira");
	@FXML
	private TableColumn<CoinUser, String> colName = new TableColumn<>("Nome");
	@FXML
	private TableColumn<CoinUser, String> colCountryCollection = new TableColumn<>("País");
	@FXML
	private TableColumn<CoinUser, Short> colYear = new TableColumn<>("Ano");
	@FXML
	private TableColumn<CoinUser, String> colCondition = new TableColumn<>("Condição");
	@FXML
	private TableColumn<CoinUser, String> colRarityCollection = new TableColumn<>("Raridade");
	@FXML
	private TableColumn<CoinUser, Boolean> colIsForSale = new TableColumn<>("A venda?");
	@FXML
	private TableColumn<CoinUser, Float> colPrice = new TableColumn<>("Preço");
	@FXML
	private TableColumn<CoinUser, String> colNotes = new TableColumn<>("Notas");

	// Coins
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

	// Market
	@FXML
	private TableView<CoinUser> tbMarket;
	@FXML
	private TableColumn<CoinUser, Blob> colImgFrontMkt = new TableColumn<>("Imagem Frontal");
	@FXML
	private TableColumn<CoinUser, Blob> colImgBackMkt = new TableColumn<>("Imagem Traseira");
	@FXML
	private TableColumn<CoinUser, String> colNameMkt = new TableColumn<>("Nome");
	@FXML
	private TableColumn<CoinUser, String> colCountryMkt = new TableColumn<>("País");
	@FXML
	private TableColumn<CoinUser, Short> colYearMkt = new TableColumn<>("Ano");
	@FXML
	private TableColumn<CoinUser, String> colConditionMkt = new TableColumn<>("Condição");
	@FXML
	private TableColumn<CoinUser, String> colRarityMkt = new TableColumn<>("Raridade");
	@FXML
	private TableColumn<CoinUser, Float> colPriceMkt = new TableColumn<>("Preço");
	@FXML
	private TableColumn<CoinUser, String> colNotesMkt = new TableColumn<>("Notas");

	private double x, y = 0;

	public void initialize() {
		boxEditGender.getItems().add(Gender.Feminino);
		boxEditGender.getItems().add(Gender.Masculino);
		boxEditGender.getItems().add(Gender.Outro);
		fixImage(profilePhoto, true);
		fixImage(editProfilePhoto, true);
		loadCoinTable();
		loadCoinUserTable();
	}

	public void loadUser(User newUser) {
		user = newUser;
		lblName.setText(user.getFirstName() + " " + user.getLastName());
		try {
			profilePhoto.setImage(new Image(Util.convertFromBlob(user.getProfilePhoto())));
			editProfilePhoto.setImage(new Image(Util.convertFromBlob(user.getProfilePhoto())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadEditableUser() {
		txtEditFirstName.setText(user.getFirstName());
		txtEditLastName.setText(user.getLastName());
		txtEditBirthDate.setValue(user.getBirthDate());
		txtEditCpf.setText(user.getCpf());
		checkCpf();
		boxEditGender.setValue(boxEditGender.getItems().get(boxEditGender.getItems().indexOf(user.getGender())));
		;
		txtEditUsername.setText(user.getUsername());
		txtEditEmail.setText(user.getEmail());

		txtEditCpf.setDisable(true);
		txtEditUsername.setDisable(true);
		txtEditEmail.setDisable(true);
		loadUser(user);
	}

	public void chooseFile(ActionEvent e) throws SerialException, SQLException {
		FileChooser fc = new FileChooser();
		fc.setTitle("Selecionar imagem");
		fc.setInitialDirectory(null);
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
		File file = fc.showOpenDialog(btnFileChooser.getScene().getWindow());
		try {
			if (file != null) {
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

	public void loadCoinTable() {
		tbCoin.getItems().clear();
		tbCoin.getColumns().clear();

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

	public void loadCoinUserTable() {
		tbCoinUser.getItems().clear();
		tbCoinUser.getColumns().clear();

		coinUserService = new CoinUserService();
		for (CoinUser coinUser : coinUserService.findAll()) {
			obsCoinUserList.add(coinUser);
		}

		colImgFront.setCellValueFactory(new PropertyValueFactory<>("imageFront"));
		colImgFront.setCellFactory(column -> new TableCell<CoinUser, Blob>() {
			private final ImageView imageView = new ImageView();
			{
				imageView.setFitWidth(100);
				imageView.setFitHeight(100);
			}

			@Override
			protected void updateItem(Blob item, boolean empty) {
				super.updateItem(item, empty);
				if (getTableRow().getItem() == null) {
					imageView.setImage(null);
				} else if (empty || item == null) {
					imageView.setImage(new Image(getClass().getResourceAsStream("../icon/no_image.png")));
				} else {
					try {
						imageView.setImage(new Image(Util.convertFromBlob(item)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				setGraphic(imageView);
			}
		});
		colImgBack.setCellValueFactory(new PropertyValueFactory<>("imageBack"));
		colImgBack.setCellFactory(column -> new TableCell<CoinUser, Blob>() {
			private final ImageView imageView = new ImageView();
			{
				imageView.setFitWidth(100);
				imageView.setFitHeight(100);
			}

			@Override
			protected void updateItem(Blob item, boolean empty) {
				super.updateItem(item, empty);
				if (getTableRow().getItem() == null) {
					imageView.setImage(null);
				} else if (empty || item == null) {
					imageView.setImage(new Image(getClass().getResourceAsStream("../icon/no_image.png")));
				} else {
					try {
						imageView.setImage(new Image(Util.convertFromBlob(item)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				setGraphic(imageView);
			}
		});

		colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoin().getName()));
		colCountryCollection.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCoin().getCountry().getName()));
		colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
		colCondition.setCellValueFactory(new PropertyValueFactory<>("coinCondition"));
		colRarityCollection.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCoin().getRarity().toString()));
		colIsForSale.setCellValueFactory(new PropertyValueFactory<>("forSale"));
		colIsForSale.setCellFactory(column -> new TableCell<CoinUser, Boolean>() {
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item ? "Sim" : "Não");
			}
		});
		colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		colPrice.setCellFactory(column -> new TableCell<CoinUser, Float>() {
			@Override
			protected void updateItem(Float item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item == 0 ? "Não Possui" : String.format("R$%.2f", item));
			}
		});
		colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
		colNotes.setCellFactory(column -> new TableCell<CoinUser, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (getTableRow().getItem() == null) {
					setText(null);
				} else {
					setText(empty || item == null || item.isEmpty() ? "Não possui" : item);
				}
			}
		});

		tbCoinUser.getColumns().addAll(colImgFront, colImgBack, colName, colCountryCollection, colYear, colCondition,
				colRarityCollection,
				colIsForSale, colPrice, colNotes);
		tbCoinUser.setItems(obsCoinUserList);
	}

	public void loadMarketTable() {
		tbMarket.getItems().clear();
		tbMarket.getColumns().clear();

		coinUserService = new CoinUserService();
		for (CoinUser coinUser : coinUserService.findAllForSale()) {
			obsCoinUserListMkt.add(coinUser);
		}

		colImgFrontMkt.setCellValueFactory(new PropertyValueFactory<>("imageFront"));
		colImgFrontMkt.setCellFactory(column -> new TableCell<CoinUser, Blob>() {
			private final ImageView imageView = new ImageView();
			{
				imageView.setFitWidth(100);
				imageView.setFitHeight(100);
			}

			@Override
			protected void updateItem(Blob item, boolean empty) {
				super.updateItem(item, empty);
				if (getTableRow().getItem() == null) {
					imageView.setImage(null);
				} else if (empty || item == null) {
					imageView.setImage(new Image(getClass().getResourceAsStream("../icon/no_image.png")));
				} else {
					try {
						imageView.setImage(new Image(Util.convertFromBlob(item)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				setGraphic(imageView);
			}
		});
		colImgBackMkt.setCellValueFactory(new PropertyValueFactory<>("imageBack"));
		colImgBackMkt.setCellFactory(column -> new TableCell<CoinUser, Blob>() {
			private final ImageView imageView = new ImageView();
			{
				imageView.setFitWidth(100);
				imageView.setFitHeight(100);
			}

			@Override
			protected void updateItem(Blob item, boolean empty) {
				super.updateItem(item, empty);
				if (getTableRow().getItem() == null) {
					imageView.setImage(null);
				} else if (empty || item == null) {
					imageView.setImage(new Image(getClass().getResourceAsStream("../icon/no_image.png")));
				} else {
					try {
						imageView.setImage(new Image(Util.convertFromBlob(item)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				setGraphic(imageView);
			}
		});

		colNameMkt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCoin().getName()));
		colCountryMkt.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCoin().getCountry().getName()));
		colYearMkt.setCellValueFactory(new PropertyValueFactory<>("year"));
		colConditionMkt.setCellValueFactory(new PropertyValueFactory<>("coinCondition"));
		colRarityMkt.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getCoin().getRarity().toString()));
		colPriceMkt.setCellValueFactory(new PropertyValueFactory<>("price"));
		colPriceMkt.setCellFactory(column -> new TableCell<CoinUser, Float>() {
			@Override
			protected void updateItem(Float item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item == 0 ? "Não Possui" : String.format("R$%.2f", item));
			}
		});
		colNotesMkt.setCellValueFactory(new PropertyValueFactory<>("notes"));
		colNotesMkt.setCellFactory(column -> new TableCell<CoinUser, String>() {
			@Override
			protected void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);
				if (getTableRow().getItem() == null) {
					setText(null);
				} else {
					setText(empty || item == null || item.isEmpty() ? "Não possui" : item);
				}
			}
		});

		tbMarket.getColumns().addAll(colImgFrontMkt, colImgBackMkt, colNameMkt, colCountryMkt, colYearMkt, colConditionMkt,
				colRarityMkt, colPrice, colNotes);
		tbMarket.setItems(obsCoinUserListMkt);
	}

	public void searchCoin() {
		if (txtCoinSearch.getText().isEmpty()) {
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
			stage.centerOnScreen();
		}
	}

	public void reloadSystem(ActionEvent e) {

	}

	public void saveUser(){
		UserService userService = new UserService();
		if(!txtEditFirstName.getText().isBlank() && !txtEditLastName.getText().isBlank()){
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Confirmação de atualização");
			alert.setContentText("Deseja realmente atualizar seus dados?");

			if (alert.showAndWait().get() == ButtonType.OK) {
				user.setFirstName(txtEditFirstName.getText());
				user.setLastName(txtEditLastName.getText());
				if(userService.save(user)){
					alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Sucesso!");
					alert.setHeaderText("Dados atualizads com sucesso!");
					alert.setContentText("Os dados de usuário foram atualizados com sucesso!");
					alert.showAndWait();
					lblSelectedFile.setText("");
					loadUser(user);
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erro!");
					alert.setHeaderText("Erro ao atualizar dados!");
					alert.setContentText("Os dados de usuário não puderam ser salvos!");
					alert.showAndWait();
				}
			}
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erro!");
			alert.setHeaderText("Erro ao atualizar dados!");
			alert.setContentText("Preencha todos os campos");
			alert.showAndWait();
		}
	}

	public void saveCoin(Event e, Coin coin) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/numismaster/view/RegisterCoin.fxml"));
		root = loader.load();
		RegisterCoinController rcc = loader.getController();
		rcc.setCoin(coin);
		if (stageRegisterCoin == null || !stageRegisterCoin.isShowing()) {
			stageRegisterCoin = new Stage();
			Scene scene = new Scene(root);
			stageRegisterCoin.initStyle(StageStyle.UNDECORATED);
			stageRegisterCoin.setTitle("Numismaster");
			stageRegisterCoin.setAlwaysOnTop(true);
			stageRegisterCoin.getIcons().add(new Image("/com/numismaster/icon/large-app-icon.png"));
			stageRegisterCoin.setScene(scene);
			stageRegisterCoin.show();
			stageRegisterCoin.centerOnScreen();
		}
	}

	public void registerCoin(Event e) throws IOException{
		selectedItem = tbCoin.getSelectionModel().getSelectedItem();

		if (selectedItem != null && selectedItem.equals(lastSelectedItem)) {
			clickCount++;
		} else {
			clickCount = 1;
		}

		if (clickCount == 2) {
			saveCoin(e, selectedItem);
			clickCount = 0;
		}

		lastSelectedItem = selectedItem;
	}

	@FXML
	private void checkCpf() {
		MaskTextField mtf = new MaskTextField();
		mtf.setMask("###.###.###-##");
		mtf.setValidCharacters("0123456789");
		mtf.setTf(txtEditCpf);
		mtf.formatter();
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
