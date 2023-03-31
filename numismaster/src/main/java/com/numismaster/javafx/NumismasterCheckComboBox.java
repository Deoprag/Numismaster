package com.numismaster.javafx;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class NumismasterCheckComboBox<T> extends CheckComboBox<T> {

    public NumismasterCheckComboBox(ObservableList<T> items, double width, double height, double x, double y){
        super(items);
        setPrefWidth(width);
		setPrefHeight(height);
        setLayoutX(x);
		setLayoutY(y);
        setBackground(new Background(new BackgroundFill(Color.rgb(217, 217, 217), CornerRadii.EMPTY, Insets.EMPTY)));
    }    
}
