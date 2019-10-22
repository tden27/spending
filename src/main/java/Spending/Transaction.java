package Spending;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Transaction {

    // Категория транзакции
    protected static ArrayList<String> categories;
    static {
        try {
            categories = DataCategories.dataCategory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int numCat;    // Номер категории транзакции
    private double sum;   // Сумма транзакции
    private static LocalDate date;
    private String comment; // Комментарий к транзакции

    Transaction(int numCat, double sum, LocalDate date, String comment) {
        this.numCat = numCat;
        this.sum = sum;
        this.date = date;
        this.comment = comment;
    }

    String getCategory() {
        return categories.get(numCat);
    }

    Double getSum() {
        return sum;
    }

    String getLocalDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("dd.MM.YYYY");
        String textDate = date.format(formatter);
        return textDate;
    }

    String getComment() {
        return comment;
    }
}
