package Spending;

import Spending.model.Transaction;

import java.sql.*;

public class OutToDB {

    private static Connection connectionToDB() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/spending?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    // создание таблицы расходов
    private static void createTableInDB() {
        try {
            try (Statement statement = connectionToDB().createStatement()) {
                statement.executeUpdate("CREATE TABLE costs (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "data DATE, " +
                            "category VARCHAR(20), " +
                            "value DOUBLE)");;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // добавление расхода в таблицу
    public static void addValueIntoDB(Transaction transaction) {
        createTableInDB();
        String SQLcommand = "INSERT INTO costs (data, category, value) VALUES (" +
                "'" + Date.valueOf(transaction.getLocalDateD()) + "', " +
                "'" + transaction.getCategory() + "', " +
                transaction.getSum() + ")";
        try {
            try (Statement statement = connectionToDB().createStatement()) {
                statement.executeUpdate(SQLcommand);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
