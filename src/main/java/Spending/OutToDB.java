package Spending;

import Spending.model.Transaction;

import java.sql.*;
import java.time.LocalDate;

public class OutToDB {
    public static void main(String[] args) {
        OutToDB outToDB = new OutToDB();
        outToDB.createTableInDB();
        outToDB.addValueIntoDB(new Transaction(1, 250, LocalDate.now(), "rrr"));
        outToDB.addValueIntoDB(new Transaction(2, 400, LocalDate.now(), "r1r"));
        outToDB.addValueIntoDB(new Transaction(1, 230, LocalDate.now(), "tre"));
        outToDB.addValueIntoDB(new Transaction(5, 20, LocalDate.now(), "te"));
    }

    private static Connection connectionToDB() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/spending?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    private static void createTableInDB() {
        try {
            try {
                Statement statement = connectionToDB().createStatement();
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

    public static void addValueIntoDB(Transaction transaction) {
        createTableInDB();
        String SQLcommand = "INSERT INTO costs (data, category, value) VALUES (" +
                "'" + Date.valueOf(transaction.getLocalDateD()) + "', " +
                "'" + transaction.getCategory() + "', " +
                transaction.getSum() + ")";
        System.out.println(SQLcommand);
        try {
            try {
                Statement statement = connectionToDB().createStatement();
                statement.executeUpdate(SQLcommand);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
