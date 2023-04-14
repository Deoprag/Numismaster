package com.numismaster.service;

import com.numismaster.model.Country;
import com.numismaster.repository.CountryRepository;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CountryService {
    private CountryRepository countryRepository;

    public CountryService(){
        countryRepository = new CountryRepository();
    }

    public boolean save(Country country){
        if(countryRepository.findByName(country.getName()) != null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("OPS...");
            alert.setHeaderText("Impossivel cadastrar país.");
            alert.setContentText("Já existe um país cadastrado com esse nome!");
            alert.showAndWait();
        } else {
            if(countryRepository.findByCode(country.getCode()) != null){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Impossivel cadastrar país.");
                alert.setContentText("Já existe um país cadastrado com esse código!");
                alert.showAndWait();
            } else {
                if(country.getId() == 0){
                    countryRepository.insert(country);
                } else {
                    countryRepository.update(country);
                }
                return true;
            }
        }
        return false;
    }

    public Country findById(int id) {
        return countryRepository.findById(id);
    }

    public Country findByName(String name) {
        return countryRepository.findByName(name);
    }

}

