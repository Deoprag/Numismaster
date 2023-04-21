package com.numismaster.service;

import java.util.List;

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
        if(country.getId() == 0){
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
                    return countryRepository.insert(country);
                }
            }
        } else {
            return countryRepository.update(country);
        }
        return false;
    }

    public boolean delete(Country country){
        if(country.getId() == 0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("OPS...");
            alert.setHeaderText("Impossivel deletar país.");
            alert.setContentText("O país não está cadastrado no sistema!");
            alert.showAndWait();
        } else {
            countryRepository.delete(country.getId());
        }
        return true;
    }

    public Country findById(int id) {
        return countryRepository.findById(id);
    }

    public Country findByName(String name) {
        return countryRepository.findByName(name);
    }

    public List<Country> findAll(){
        return countryRepository.findAll();
    }
}

