package com.numismaster.javafx;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class NumismasterCheckComboBox<T> extends CheckComboBox<T> {

    public NumismasterCheckComboBox(ObservableList<T> items, double width, double height, double x, double y){
        super(items);
        setPrefWidth(width);
		setPrefHeight(height);
        setLayoutX(x);
		setLayoutY(y);
        setStyle("-fx-body-color: #d9d9d9;" + 
        "-fx-selection-bar: #0096c9;" +
        "fx-border-style: hidden;");
        setBorder(new Border(new BorderStroke(Color.valueOf("#373c3f"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(0, 0, 3, 0))));
    }    
}
