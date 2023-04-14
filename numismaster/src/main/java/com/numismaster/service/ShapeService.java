package com.numismaster.service;

import com.numismaster.repository.ShapeRepository;

public class ShapeService {
    private ShapeRepository shapeRepository;

    public ShapeService(){
        shapeRepository = new ShapeRepository();
    }
    
}
