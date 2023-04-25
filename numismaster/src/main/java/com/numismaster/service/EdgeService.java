package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Edge;
import com.numismaster.repository.CoinEdgeRepository;
import com.numismaster.repository.EdgeRepository;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class EdgeService {
    private EdgeRepository edgeRepository;

    public EdgeService(){
        edgeRepository = new EdgeRepository();
    }

    public boolean save(Edge edge){
        if(edge.getId() == 0){
            if(findByName(edge.getName()) != null){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Borda duplicada.");
                alert.setContentText("O nome de borda informado já está cadastrado no sistema.");
            } else {
                return edgeRepository.insert(edge);
            }
        } else {
            return edgeRepository.update(edge);
        }
        return false;
    }

    public boolean delete(Edge edge){
        if (edge.getId() == 0){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("OPS...");
            alert.setHeaderText("Impossivel deletar borda.");
            alert.setContentText("A borda não está cadastrada no sistema!");
            alert.showAndWait();
        } else {
            CoinEdgeRepository coinEdgeRepository = new CoinEdgeRepository();
            if(coinEdgeRepository.findCoinsByEdge(edge).size() > 0){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("Não é possível excluir a borda.");
                alert.setContentText("A borda está sendo utilizada por alguma moeda no sistema.");
                alert.showAndWait();
            } else {
                return edgeRepository.delete(edge.getId());
            }
        }
        return false;
    }

    public Edge findByName(String name){
        return edgeRepository.findByName(name);
    }

    public List<Edge> findAll(){
        return edgeRepository.findAll();
    }
}
