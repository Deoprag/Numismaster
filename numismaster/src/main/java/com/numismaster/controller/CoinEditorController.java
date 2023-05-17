package com.numismaster.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import com.numismaster.model.Coin;
import com.numismaster.model.CoinCondition;
import com.numismaster.model.CoinUser;
import com.numismaster.model.User;
import com.numismaster.service.CoinUserService;
import com.numismaster.util.Util;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import lombok.Setter;

@Setter
public class CoinEditorController {
    
    private MainMenuController mainMenuController;
    private Coin coin;
    private User user;
    private CoinUser coinUser = new CoinUser();

    @FXML
    private Button btnClose;
    @FXML
    private Button btnMinimize;
    @FXML
	private Pane paneBar;

    private double x, y;
    
    @FXML
    private Label lblCoinName;
    @FXML
    private TextField txtCoinYear;
    @FXML
    private ChoiceBox<CoinCondition> boxCondition;
    @FXML
    private CheckBox checkForSale;
    @FXML
    private TextField txtCoinPrice;
    @FXML
    private Button btnSelectFrontImage;
    @FXML
    private Label lblFrontalImage;
    @FXML
    private Button btnSelectBackImage;
    @FXML
    private Label lblBackImage;
    @FXML
    private TextArea txtNotes;

    @FXML
    private Button btnDelete;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnUpdate;

    public void initialize(){
        boxCondition.getItems().addAll(CoinCondition.values());
        checkInputs();
    }

    public void checkInputs(){
        txtCoinYear.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^0-9]", "");
			if (!newValue.equals(filteredValue)) {
				txtCoinYear.setText(filteredValue);
			}
		});
        txtCoinPrice.textProperty().addListener((observable, oldValue, newValue) -> {
			String filteredValue = newValue.replaceAll("[^0-9,.]", "");
			if (!newValue.equals(filteredValue)) {
				txtCoinPrice.setText(filteredValue);
			}
		});	
    }

    public void loadCoin(Coin newCoin){
        coin = newCoin;
        lblCoinName.setText(coin.getName().toUpperCase());
        btnRegister.setVisible(true);
        btnRegister.setDisable(false);
    }

    public void loadUser(User newUser, MainMenuController mmc){
        user = newUser;
        mainMenuController = mmc;
    }

    public void loadCoinUser(CoinUser newCoinUser){
        coinUser = newCoinUser;
        coin = newCoinUser.getCoin();
        lblCoinName.setText(coin.getName().toUpperCase());
        btnDelete.setVisible(true);
        btnDelete.setDisable(false);
        btnUpdate.setVisible(true);
        btnUpdate.setDisable(false);

        txtCoinYear.setText(String.valueOf(coinUser.getYear()));
        checkForSale.setSelected(coinUser.isForSale());
        if(checkForSale.isSelected()){
            txtCoinPrice.setText(String.valueOf(coinUser.getPrice()));
            txtCoinPrice.setDisable(false);
        }
        boxCondition.setValue(coinUser.getCoinCondition());
        txtNotes.setText(coinUser.getNotes());
    }
    
    public void registerCoin(){
        CoinUserService coinUserService = new CoinUserService();
        coinUser.setUser(user);
        coinUser.setCoin(coin);
        coinUser.setYear(Short.parseShort(txtCoinYear.getText()));
        coinUser.setCoinCondition(boxCondition.getValue());
        coinUser.setForSale(checkForSale.isSelected());
        if(coinUser.isForSale()){
            coinUser.setPrice(Float.parseFloat(txtCoinPrice.getText().replace(",", ".")));
        }
        coinUser.setNotes(txtNotes.getText());
        if(coinUserService.save(coinUser)){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Sucesso!");
            alert.setHeaderText("Adicionado com sucesso!");
            alert.setContentText("A moeda foi adicionada à coleção com sucesso!");
            alert.showAndWait();
            btnClose.fire();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro!");
            alert.setHeaderText("Erro ao adicionar!");
            alert.setContentText("A moeda não foi adicionada à coleção.");
            alert.showAndWait();
        }
    }

    public void changeTxtPrice(){
        if(checkForSale.isSelected()){
            if(coinUser.getPrice() != null){
                txtCoinPrice.setText(coinUser.getPrice().toString());
            }
            txtCoinPrice.setDisable(false);
        } else {
            txtCoinPrice.setText("");
            txtCoinPrice.setDisable(true);
        }
    }

    public void chooseFrontImage() throws SerialException, SQLException{
        FileChooser fc = new FileChooser();
		fc.setTitle("Selecionar imagem");
		fc.setInitialDirectory(null);
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
		File file = fc.showOpenDialog(btnSelectFrontImage.getScene().getWindow());
		try {
			if(file != null){
				if (file.length() <= 2 * 1024 * 1024) {
					FileInputStream fis = new FileInputStream(file);
					coinUser.setImageFront(Util.convertToBlob(fis));
					lblFrontalImage.setText(file.getName().toString());
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

    public void chooseBackImage() throws SerialException, SQLException{
        FileChooser fc = new FileChooser();
		fc.setTitle("Selecionar imagem");
		fc.setInitialDirectory(null);
		fc.getExtensionFilters().addAll(
				new ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
		File file = fc.showOpenDialog(btnSelectBackImage.getScene().getWindow());
		try {
			if(file != null){
				if (file.length() <= 2 * 1024 * 1024) {
					FileInputStream fis = new FileInputStream(file);
					coinUser.setImageBack(Util.convertToBlob(fis));
					lblBackImage.setText(file.getName().toString());
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

    public void deleteCoin(){
        CoinUserService coinUserService = new CoinUserService();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmação");
        alert.setHeaderText("Confirmação de remoção");
        alert.setContentText("Deseja realmente remover esta moeda da sua coleção?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            if(coinUserService.delete(coinUser)){
                Alert alert2 = new Alert(AlertType.CONFIRMATION);
                alert2.setTitle("Sucesso!");
                alert2.setHeaderText("Removido com sucesso!");
                alert2.setContentText("A moeda foi removida da sua coleção com sucesso!");
                alert2.showAndWait();
                mainMenuController.loadCoinUserTable();
                btnClose.fire();
            }
        }
    }
    
    public void updateCoin(){
        
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
