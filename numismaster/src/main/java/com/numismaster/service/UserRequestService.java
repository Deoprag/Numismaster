package com.numismaster.service;

import java.util.List;

import com.numismaster.model.User;
import com.numismaster.model.UserRequest;
import com.numismaster.repository.UserRequestRepository;

public class UserRequestService {
    private UserRequestRepository userRequestRepository;

    public UserRequestService() {
        userRequestRepository = new UserRequestRepository();
    }

    public boolean save(UserRequest userRequest) {
        if (userRequest.getId() == 0) {
            return userRequestRepository.insert(userRequest);
        } else {
            return userRequestRepository.update(userRequest);
        }
    }

    public UserRequest findById(int id) {
        return userRequestRepository.findById(id);
    }

    public List<UserRequest> findAllByUser(User user) {
        return userRequestRepository.findAllByUser(user.getId());
    }

    public List<UserRequest> findAll() {
        return userRequestRepository.findAll();
    }
}
