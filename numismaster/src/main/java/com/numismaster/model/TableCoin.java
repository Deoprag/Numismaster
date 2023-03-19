package com.numismaster.model;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TableCoin {
    private ImageView imgFront;

    private ImageView imgBack;

    private String name;

    private String country;

    private short year;

    private String condition;

    private String rarity;

    private char isForSale;

    private float price;
    
    private Button details;
}
