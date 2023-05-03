package com.example.testjavabars;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static Connection connection;
    public static String SERVER_ADDRESS;
    public static int SERVER_PORT;

    public static void loadConfig() {
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            SERVER_ADDRESS = prop.getProperty("SERVER_ADDRESS");
            SERVER_PORT = Integer.parseInt(prop.getProperty("SERVER_PORT"));
            DB_URL = prop.getProperty("DB_URL");
            DB_USER = prop.getProperty("DB_USER");
            DB_PASSWORD = prop.getProperty("DB_PASSWORD");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

}