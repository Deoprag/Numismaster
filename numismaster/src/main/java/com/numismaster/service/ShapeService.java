package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Shape;
import com.numismaster.repository.ShapeRepository;

public class ShapeService {
    private ShapeRepository shapeRepository;

    public ShapeService(){
        shapeRepository = new ShapeRepository();
    }

    public List<Shape> findAll(){
        return shapeRepository.findAll();
    }
    
}
