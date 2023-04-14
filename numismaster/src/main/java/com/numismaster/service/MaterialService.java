package com.numismaster.service;

import com.numismaster.repository.MaterialRepository;

public class MaterialService {
    private MaterialRepository materialRepository;

    public MaterialService(){
        materialRepository = new MaterialRepository();
    }
}
