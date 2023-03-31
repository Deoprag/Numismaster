// package com.numismaster.model;

// import javafx.beans.property.DoubleProperty;
// import javafx.beans.property.ListProperty;
// import javafx.beans.property.ObjectProperty;
// import javafx.beans.property.SimpleDoubleProperty;
// import javafx.beans.property.SimpleListProperty;
// import javafx.beans.property.SimpleObjectProperty;
// import javafx.beans.property.SimpleStringProperty;
// import javafx.beans.property.StringProperty;
// import javafx.collections.FXCollections;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// public class TableViewCoin {

//     private final StringProperty name;
//     private final DoubleProperty denomination;
//     private final DoubleProperty weight;
//     private final DoubleProperty diameter;
//     private final DoubleProperty thickness;
//     private final ObjectProperty<Rarity> rarity;
//     private final ObjectProperty<Country> country;
//     private final ListProperty<CoinShape> shape;
//     private final ListProperty<CoinMaterial> materials;
//     private final ObjectProperty<Edge> edge;

//     public TableViewCoin(Coin coin) {
//         this.name = new SimpleStringProperty(coin.getName());
//         this.denomination = new SimpleDoubleProperty(coin.getDenomination());
//         this.weight = new SimpleDoubleProperty(coin.getWeight());
//         this.diameter = new SimpleDoubleProperty(coin.getDiameter());
//         this.thickness = new SimpleDoubleProperty(coin.getThickness());
//         this.rarity = new SimpleObjectProperty<>(coin.getRarity());
//         this.country = new SimpleObjectProperty<>(coin.getCountry());
//         this.edge = new SimpleObjectProperty<>(coin.getEdge());
//         this.shape = new SimpleListProperty<>(FXCollections.observableArrayList(coin.getCoinShapes()));
//         this.materials = new SimpleListProperty<>(FXCollections.observableArrayList(coin.getCoinMaterials()));
//     }
    
// }
