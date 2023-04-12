package com.numismaster.service;

import java.time.LocalDate;

import com.numismaster.model.User;
import com.numismaster.repository.UserRepository;
import com.numismaster.util.Validator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UserService {
    
    private UserRepository userRepository;

    public UserService(){
        userRepository = new UserRepository();
    }

    public boolean save(User user){
        if (!user.getBirthDate().isBefore(LocalDate.now().minusYears(18).plusDays(1))) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("OPS...");
            alert.setHeaderText("Impossivel cadastrar usuário.");
            alert.setContentText("Você precisa ser maior de 18 anos para se cadastrar!");
            alert.showAndWait();
        } else {
            if (!Validator.isCpf(user.getCpf())) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("OPS...");
                alert.setHeaderText("CPF Inválido.");
                alert.setContentText("Você precisa informar um CPF válido!");
                alert.showAndWait();
            } else {
                if (!Validator.isEmail(user.getEmail())) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("OPS...");
                    alert.setHeaderText("Email Inválido.");
                    alert.setContentText("Você precisa informar um email válido!");
                    alert.showAndWait();
                } else {
                    if (userRepository.findByCpf(user.getCpf()) != null) {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("OPS...");
                        alert.setHeaderText("CPF duplicado.");
                        alert.setContentText("O CPF informado já existe, escolha outro e tente novamente!");
                        alert.showAndWait();
                    } else {
                        if (userRepository.findByUsername(user.getUsername()) != null) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("OPS...");
                            alert.setHeaderText("Nome de usuário duplicado.");
                            alert.setContentText("O nome de usuário informado já existe, escolha outro e tente novamente!");
                            alert.showAndWait();
                        } else {
                            if (userRepository.findByEmail(user.getEmail()) != null) {
                                Alert alert = new Alert(AlertType.WARNING);
                                alert.setTitle("OPS...");
                                alert.setHeaderText("Email duplicado.");
                                alert.setContentText("O email informado já existe, escolha outro e tente novamente!");
                                alert.showAndWait();
                            } else {
                                if(user.getId() == null){
                                    if(userRepository.insert(user)){
                                        return true;
                                    }
                                    return false;
                                } else {
                                    if(userRepository.update(user)){
                                        return true;
                                    }
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean delete(User user){
        if(userRepository.delete(user.getId())){
            return true;
        }
        return false;
    }

}
