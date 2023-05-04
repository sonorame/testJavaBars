# Тестовое задание CIT Bars
Программа для отображения списка договоров на JavaFX.

>Существует клиент-серверное приложение с БД. С БД может взаимодействовать только сервер. Клиент с сервером должен взаимодействовать через Http. В БД должна быть таблица договоров, содержащая дату, номер и дату последнего обновления.
Необходимо написать программу, которая отображала бы список договоров. В таблице договоров должна быть колонка с признаком актуальности(CheckBox) (True если дата последнего обновления договора меньше текущей даты на 60 дней).
>Клиент должен быть написан на JavaFx

## Установка
1. Развернуть [базу](https://github.com/sonorame/testJavaBars/blob/master/out/backup/dump-Test_base.sql "DB sql backup")
2. [Скачать](https://github.com/sonorame/testJavaBars/tree/master/out/exe "клиент и сервер") клиент и сервер
3. [Скачать](https://github.com/sonorame/testJavaBars/blob/master/src/main/resources/config.properties "конфиг") и настроить конфиг<br>
Пример:<br>
`SERVER_ADDRESS=localhost`<br>
`SERVER_PORT=8005`<br>
`DB_URL=jdbc:postgresql://localhost:5432/MyDatabase`<br>
`DB_USER=postgres`<br>
`DB_PASSWORD=`<br>
4. Разместить exe файлы клиента и сервера в любом месте. **Важно** чтобы config.properties был на одном уровне с exe файлами
4. Запустить Server.exe, нажать на кнопку "Запустить сервер"
5. Запустить Client.exe, пройти авторизацию (дефолт в базе admin 111)

## Пример использования
![здесь была гифка](https://s11.gifyu.com/images/example.gif)
