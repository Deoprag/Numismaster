package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Material;
import com.numismaster.repository.MaterialRepository;

public class MaterialService {
    private MaterialRepository materialRepository;

    public MaterialService(){
        materialRepository = new MaterialRepository();
    }

    public List<Material> findAll(){
        return materialRepository.findAll();
    }
}
