package com.numismaster.service;

import com.numismaster.model.Request;
import com.numismaster.repository.RequestRepository;

public class RequestService {
    private RequestRepository RequestRepository;

    public RequestService() {
        RequestRepository = new RequestRepository();
    }

    public boolean save(Request request) {
        if (request.getId() == 0) {
            return RequestRepository.insert(request);
        } else {
            return RequestRepository.update(request);
        }
    }
}
