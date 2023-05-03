package com.example.testjavabars;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class Client extends Application {

    private static String SERVER_ADDRESS;
    private static int SERVER_PORT;

    static {
        DBConnection.loadConfig();
        SERVER_PORT = DBConnection.SERVER_PORT;
        SERVER_ADDRESS = DBConnection.SERVER_ADDRESS;
    }

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Stage loginStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Загрузка формы авторизации из файла
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        AnchorPane root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setClient(this);

        Scene scene = new Scene(root);
        loginStage = primaryStage;
        loginStage.setScene(scene);
        loginStage.setTitle("Авторизация");
        loginStage.show();
    }

    public void openTableWindow() throws IOException {
        // Открытие нового окна с табличной формой
        FXMLLoader tableLoader = new FXMLLoader(getClass().getResource("rdogovor.fxml"));
        Parent tableRoot = tableLoader.load();
        Scene scene = new Scene(tableRoot);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Договоры");
        stage.show();

        // Закрытие окна авторизации
        loginStage.close();
    }

    public void connectToServer(String login, String password) throws IOException {
        // Попытка подключения к серверу
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        // Отправка запроса на авторизацию
        out.writeUTF("SELECT user_login FROM suser WHERE user_login = '" + login + "' AND user_pass = '" + password + "'");
        String response = in.readUTF();

        // Обработка ответа от сервера
        if (response.equals("OK")) {
            // Авторизация прошла успешно
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, "Авторизация пользователя " + login + " прошла успешно", "Авторизация");
            openTableWindow();
        } else {
            // Авторизация не удалась
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Неверное имя пользователя или пароль", "Ошибка");
        }

        // Закрытие соединения с сервером
        socket.close();
        in.close();
        out.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}