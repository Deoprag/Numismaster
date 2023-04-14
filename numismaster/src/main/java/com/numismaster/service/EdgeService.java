package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Edge;
import com.numismaster.repository.EdgeRepository;

public class EdgeService {
    private EdgeRepository edgeRepository;

    public EdgeService(){
        edgeRepository = new EdgeRepository();
    }

    public List<Edge> findAll(){
        return edgeRepository.findAll();
    }
}
