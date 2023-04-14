package com.numismaster.service;

import com.numismaster.repository.EdgeRepository;

public class EdgeService {
    private EdgeRepository edgeRepository;

    public EdgeService(){
        edgeRepository = new EdgeRepository();
    }
}
