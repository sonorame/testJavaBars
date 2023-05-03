package com.example.testjavabars;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    private Client client;

    public TextField getUsernameField() {
        return usernameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @FXML
    private void handleLoginButton(ActionEvent event) {
        try {
            String login = usernameField.getText();
            String password = passwordField.getText();
            client.connectToServer(login, password);
        } catch (IOException e) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Ошибка подключения к серверу " + e.getMessage(), "Ошибка");
        }
    }
}