package Spending.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transaction {

    // Категория транзакции
    public static Map<Integer, String> categories;
    static {
        categories = DataCategories.dataCategory();
    }

    private int numCat;    // Номер категории транзакции
    private double sum;   // Сумма транзакции
    private static LocalDate date;
    private String comment; // Комментарий к транзакции

    public Transaction(int numCat, double sum, LocalDate date, String comment) {
        this.numCat = numCat;
        this.sum = sum;
        this.date = date;
        this.comment = comment;
    }

    public String getCategory() {
        return categories.get(numCat);
    }

    public Double getSum() {
        return sum;
    }

    public LocalDate getLocalDateD(){
        return date;
    }

    public String getLocalDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("dd.MM.YYYY");
        return date.format(formatter);
    }

    public String getComment() {
        return comment;
    }
}
