package Spending.model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataCategories {
    private static String fileNameOfCategories = "\\Программа учета расходов\\Spending\\src\\main\\resources\\categories.properties";

    // чтение категорий из файла
    public static Map<Integer, String> dataCategory() {
        Map<Integer, String> categoryes = new HashMap<>();

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(fileNameOfCategories));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String key : properties.stringPropertyNames()) {
            categoryes.put(Integer.parseInt(key), properties.get(key).toString());
        }
        return categoryes;
    }

    // метод добавления новых категорий расходов
    public static void addCategory(String newCategory){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(fileNameOfCategories));
            properties.setProperty(String.valueOf(properties.stringPropertyNames().size() + 1), newCategory);
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileNameOfCategories)) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                properties.store(writer, "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
