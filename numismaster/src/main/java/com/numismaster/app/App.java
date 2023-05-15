package com.numismaster.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
			Parent root = FXMLLoader.load(getClass().getResource("/com/numismaster/view/Login.fxml"));
			Scene scene = new Scene(root);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setTitle("Numismaster");
			stage.getIcons().add(new Image("/com/numismaster/icon/large-app-icon.png"));
			stage.setScene(scene);
			stage.show();
			stage.centerOnScreen();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
}