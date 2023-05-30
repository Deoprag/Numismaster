package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Material;
import com.numismaster.repository.CoinMaterialRepository;
import com.numismaster.repository.MaterialRepository;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MaterialService {
    private MaterialRepository materialRepository;

    public MaterialService() {
        materialRepository = new MaterialRepository();
    }

    public boolean save(Material material) {
        if (material.getId() == 0) {
            if (findByName(material.getName()) != null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Material duplicado.");
                alert.setContentText("O nome de material informado já está cadastrado no sistema.");
            } else {
                return materialRepository.insert(material);
            }
        } else {
            return materialRepository.update(material);
        }
        return false;
    }

    public boolean delete(Material material) {
        if (material.getId() == 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("OPS...");
            alert.setHeaderText("Impossivel deletar material.");
            alert.setContentText("O material não está cadastrada no sistema!");
            alert.showAndWait();
        } else {
            CoinMaterialRepository coinMaterialRepository = new CoinMaterialRepository();
            if (coinMaterialRepository.findCoinsByMaterial(material).size() > 0) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Não é possível excluir o material.");
                alert.setContentText("O material está sendo utilizada por alguma moeda no sistema.");
                alert.showAndWait();
            } else {
                return materialRepository.delete(material.getId());
            }
        }
        return false;
    }

    public Material findByName(String name) {
        return materialRepository.findByName(name);
    }

    public List<Material> findAll() {
        return materialRepository.findAll();
    }
}
