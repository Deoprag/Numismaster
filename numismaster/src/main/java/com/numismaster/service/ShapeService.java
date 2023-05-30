package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Shape;
import com.numismaster.repository.CoinShapeRepository;
import com.numismaster.repository.ShapeRepository;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ShapeService {
    private ShapeRepository shapeRepository;

    public ShapeService() {
        shapeRepository = new ShapeRepository();
    }

    public boolean save(Shape shape) {
        if (shape.getId() == 0) {
            if (findByName(shape.getName()) != null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Formato duplicado.");
                alert.setContentText("O nome de formato informado já está cadastrado no sistema.");
            } else {
                return shapeRepository.insert(shape);
            }
        } else {
            return shapeRepository.update(shape);
        }
        return false;
    }

    public boolean delete(Shape shape) {
        if (shape.getId() == 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("OPS...");
            alert.setHeaderText("Impossivel deletar formato.");
            alert.setContentText("O formato não está cadastrado no sistema!");
            alert.showAndWait();
        } else {
            CoinShapeRepository coinShapeRepository = new CoinShapeRepository();
            if (coinShapeRepository.findCoinsByShape(shape).size() > 0) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Não é possível excluir o formato.");
                alert.setContentText("O formato está sendo utilizada por alguma moeda no sistema.");
                alert.showAndWait();
            } else {
                return shapeRepository.delete(shape.getId());
            }
        }
        return false;
    }

    public Shape fndById(int id) {
        return shapeRepository.findById(id);
    }

    public Shape findByName(String name) {
        return shapeRepository.findByName(name);
    }

    public List<Shape> findAll() {
        return shapeRepository.findAll();
    }
}
