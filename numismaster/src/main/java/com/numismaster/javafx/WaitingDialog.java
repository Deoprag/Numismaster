package com.numismaster.javafx;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WaitingDialog {

    private final Stage dialogStage;
    private final ProgressIndicator progressIndicator;

    public WaitingDialog(Task<?> task) {
        dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.setResizable(false);

        Label label = new Label("Aguarde...");
        label.setStyle("-fx-text-fill: #d9d9d9; -fx-font-size: 16pt;");

        progressIndicator = new ProgressIndicator();
        progressIndicator.setStyle("-fx-progress-color: #d9d9d9;");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        hBox.getChildren().addAll(progressIndicator, label);
        hBox.setStyle("-fx-background-color: #565e62; -fx-padding: 10px;");

        StackPane stackPane = new StackPane(hBox);
        stackPane.setStyle("-fx-background-color: transparent;");
        stackPane.setAlignment(Pos.CENTER);

        dialogStage.setScene(new Scene(stackPane, 200, 60));
    }

    public void show() {
        dialogStage.show();
    }

    public void close() {
        dialogStage.close();
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }
}