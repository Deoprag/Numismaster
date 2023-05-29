package com.numismaster.service;

import com.numismaster.model.UserRequest;
import com.numismaster.repository.UserRequestRepository;

public class UserRequestService {
    private UserRequestRepository userRequestRepository;

    public UserRequestService(){
        userRequestRepository = new UserRequestRepository();
    }

    public boolean save(UserRequest userRequest) {
        if(userRequest.getId() == 0){
            return userRequestRepository.insert(userRequest);
        } else {
            return userRequestRepository.update(userRequest);
        }
    }
}
