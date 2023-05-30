package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Coin;
import com.numismaster.repository.CoinRepository;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CoinService {
    private CoinRepository coinRepository;

    public CoinService() {
        coinRepository = new CoinRepository();
    }

    public boolean save(Coin coin) {
        if (coin.getId() == 0) {
            if (findByName(coin.getName()) != null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Moeda duplicada.");
                alert.setContentText("O nome de moeda informado já está cadastrado no sistema.");
                alert.showAndWait();
            } else {
                return coinRepository.insert(coin);
            }
        } else {
            return coinRepository.update(coin);
        }
        return false;
    }

    public boolean delete(Coin coin) {
        CoinUserService coinUserService = new CoinUserService();
        if (coinUserService.findUsersByCoin(coin).size() > 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("OPS...");
            alert.setHeaderText("Não é possível excluir a moeda.");
            alert.setContentText("A moeda está sendo utilizada por algum usuário no sistema.");
            alert.showAndWait();
        } else {
            if (coin.getId() == 0) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Não é possível excluir a moeda.");
                alert.setContentText("A moeda não está cadastrada no sistema.");
                alert.showAndWait();
            }
            return coinRepository.delete(coin.getId());
        }
        return false;
    }

    public Coin findByName(String name) {
        return coinRepository.findByName(name);
    }

    public List<Coin> findAll() {
        return coinRepository.findAll();
    }
}
