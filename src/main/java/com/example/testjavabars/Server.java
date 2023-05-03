package com.example.testjavabars;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server extends Application {

    private static int PORT;

    static {
        DBConnection.loadConfig();
        PORT = DBConnection.SERVER_PORT;
    }

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    private Connection connection;
    private Statement statement;

    private Label statusLabel;
    private Button startStopButton;

    private boolean isServerRunning = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Создание окна с информацией о сервере
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 300, 200);

        statusLabel = new Label("Сервер не запущен");
        root.setCenter(statusLabel);

        startStopButton = new Button("Запустить сервер");
        startStopButton.setOnAction(event -> {
            if (isServerRunning) {
                stopServer();
            } else {
                startServer();
            }
        });
        root.setBottom(startStopButton);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Сервер");
        primaryStage.setOnCloseRequest(event -> {
            stopServer();
        });
        primaryStage.show();
    }

    private void startServer() {
        new Thread(() -> {
            try {
                // Создание серверного сокета
                serverSocket = new ServerSocket(PORT);
                Platform.runLater(() -> {
                    statusLabel.setText("Сервер запущен на порту " + PORT);
                    startStopButton.setText("Остановить сервер");
                });
                isServerRunning = true;

                // Подключение к БД
                connection = DBConnection.getConnection();
                statement = connection.createStatement();

                // Ожидание подключения клиента
                while (isServerRunning) {
                    clientSocket = serverSocket.accept();
                    Platform.runLater(() -> statusLabel.setText("Сигнал от клиента"));

                    // Создание потоков ввода-вывода для обмена данными с клиентом
                    in = new DataInputStream(clientSocket.getInputStream());
                    out = new DataOutputStream(clientSocket.getOutputStream());

                    // Обработка запроса от клиента
                    String query = in.readUTF();
                    ResultSet resultSet = statement.executeQuery(query);

                    // Отправка ответа клиенту
                    if (resultSet.next()) {
                        out.writeUTF("OK");
                    } else {
                        out.writeUTF("ERROR");
                    }

                    // Закрытие соединения с клиентом
                    clientSocket.close();
                    in.close();
                    out.close();
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    // Закрытие серверного сокета и соединения с БД
                    serverSocket.close();
                    connection.close();
                    statement.close();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void stopServer() {
        isServerRunning = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            statusLabel.setText("Сервер остановлен");
            startStopButton.setText("Запустить сервер");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}