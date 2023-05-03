package com.example.testjavabars;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class RdogovorController {

    private Connection connection;
    private Statement statement;

    @FXML
    private TableView<Rdogovor> tableView;
    @FXML
    private TableColumn<Rdogovor, Timestamp> dateStartColumn;
    @FXML
    private TableColumn<Rdogovor, Timestamp> updateTimeColumn;
    @FXML
    private TableColumn<Rdogovor, String> dogNoColumn;
    @FXML
    private void initialize() {
        try {
            dateStartColumn.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
            updateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("updateTime"));
            dogNoColumn.setCellValueFactory(new PropertyValueFactory<>("dogNo"));

            //отображаем дату в формате dd.MM.yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            StringConverter<Timestamp> converter = new StringConverter<Timestamp>() {
                @Override
                public String toString(Timestamp timestamp) {
                    if (timestamp != null) {
                        return formatter.format(timestamp.toLocalDateTime().toLocalDate());
                    } else {
                        return "";
                    }
                }
                @Override
                public Timestamp fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        LocalDate localDate = LocalDate.parse(string, formatter);
                        return Timestamp.valueOf(localDate.atStartOfDay());
                    } else {
                        return null;
                    }
                }
            };
            dateStartColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));
            updateTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(converter));

            // Подключение к БД
            connection = DBConnection.getConnection();
            statement = connection.createStatement();

            // Выполнение запроса на получение данных для таблицы
            ResultSet resultSet = statement.executeQuery("select dog_no, date_start, update_time from rdogovor;");
            // Заполнение таблицы данными
            while (resultSet.next()) {
                Rdogovor rdogovor = new Rdogovor(
                        resultSet.getString("dog_no"),
                        resultSet.getTimestamp("date_start"),
                        resultSet.getTimestamp("update_time")
                );
                tableView.getItems().add(rdogovor);
            }
            //ставим true если дата последнего обновления меньше 60 дней от текущей даты.
            LocalDate currentDate = LocalDate.now();
            for (Rdogovor rdogovor : tableView.getItems()) {
                if (rdogovor.getUpdateTime() != null) {
                    LocalDate updateDate = rdogovor.getUpdateTime().toLocalDateTime().toLocalDate();
                    long daysBetween = ChronoUnit.DAYS.between(updateDate, currentDate);
                    if (daysBetween < 60) {
                        rdogovor.setAktualnost(true);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertHelper.showAlert(Alert.AlertType.ERROR, "Ошибка при выполнении запроса к БД: " + e.getMessage(), "ошибка");
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Rdogovor {
        private String dogNo;
        private Timestamp dateStart;
        private Timestamp updateTime;
        private boolean aktualnost;

        public Rdogovor(String dogNo, Timestamp dateStart, Timestamp updateTime) {
            this.dogNo = dogNo;
            this.dateStart = dateStart;
            this.updateTime = updateTime;
        }

        public String getDogNo() {
            return dogNo;
        }

        public void setDogNo(String dogNo) {
            this.dogNo = dogNo;
        }

        public Timestamp getDateStart() {
            return dateStart;
        }

        public void setDateStart(Timestamp dateStart) {
            this.dateStart = dateStart;
        }

        public Timestamp getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Timestamp updateTime) {
            this.updateTime = updateTime;
        }

        public boolean isAktualnost() {
            return aktualnost;
        }

        public void setAktualnost(boolean aktualnost) {
            this.aktualnost = aktualnost;
        }
    }
}