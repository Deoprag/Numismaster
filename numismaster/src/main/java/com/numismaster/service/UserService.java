package com.numismaster.service;

import java.time.LocalDate;
import java.util.Optional;

import com.numismaster.model.User;
import com.numismaster.repository.UserRepository;
import com.numismaster.util.Email;
import com.numismaster.util.Util;
import com.numismaster.util.Validator;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public boolean save(User user) {
        if (user.getId() == null) {
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
                        if (findByCpf(user.getCpf()) != null) {
                            Alert alert = new Alert(AlertType.WARNING);
                            alert.setTitle("OPS...");
                            alert.setHeaderText("CPF duplicado.");
                            alert.setContentText("O CPF informado já existe, escolha outro e tente novamente!");
                            alert.showAndWait();
                        } else {
                            if (findByUsername(user.getUsername()) != null) {
                                Alert alert = new Alert(AlertType.WARNING);
                                alert.setTitle("OPS...");
                                alert.setHeaderText("Nome de usuário duplicado.");
                                alert.setContentText(
                                        "O nome de usuário informado já existe, escolha outro e tente novamente!");
                                alert.showAndWait();
                            } else {
                                if (findByEmail(user.getEmail()) != null) {
                                    Alert alert = new Alert(AlertType.WARNING);
                                    alert.setTitle("OPS...");
                                    alert.setHeaderText("Email duplicado.");
                                    alert.setContentText(
                                            "O email informado já existe, escolha outro e tente novamente!");
                                    alert.showAndWait();
                                } else {
                                    Email email = new Email();
                                    String code = Util.generateCode();
                                    if (email.sendConfirmationCode(code, user.getEmail(), user.getFirstName())) {
                                        int i = 0;
                                        do {
                                            i++;
                                            TextInputDialog td = new TextInputDialog();
                                            td.setTitle("Finalizar cadastro. Tentativa: " + i + "/3");
                                            td.setHeaderText("Insira o código de confirmação enviado no email: "
                                                    + user.getEmail());
                                            td.setContentText("Código: ");

                                            Optional<String> result = td.showAndWait();
                                            if (result.isPresent()) {
                                                String name = result.get();
                                                if (code.equals(name)) {
                                                    return userRepository.insert(user);
                                                } else {
                                                    if (i >= 3) {
                                                        Alert alert = new Alert(AlertType.ERROR);
                                                        alert.setTitle("ERRO!");
                                                        alert.setHeaderText("Código incorreto!");
                                                        alert.setContentText(
                                                                "Você errou o código 3 vezes. Infelizmente não foi possivel finalizar o cadastro!");
                                                        alert.showAndWait();
                                                    } else {
                                                        Alert alert = new Alert(AlertType.ERROR);
                                                        alert.setTitle("ERRO!");
                                                        alert.setHeaderText("Código incorreto!");
                                                        alert.setContentText("Código inválido. Tente novamente!");
                                                        alert.showAndWait();
                                                    }
                                                }
                                            } else {
                                                Alert alert = new Alert(AlertType.ERROR);
                                                alert.setTitle("OBS");
                                                alert.setHeaderText("Operação cancelada!");
                                                alert.setContentText("Você cancelou a operação!");
                                                alert.showAndWait();
                                                break;
                                            }
                                        } while (i < 3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (userRepository.update(user)) {
                return true;
            }
        }
        return false;
    }

    public void blockUser(String username){
        User user = findByUsername(username);
        user.setBlocked(true);
        userRepository.update(user);
    }

    public boolean delete(User user) {
        if (userRepository.delete(user.getId())) {
            return true;
        }
        return false;
    }

    public User login(String username, String password) {
        return userRepository.login(username, password);
    }

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
