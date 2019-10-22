package Spending;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class DataCategories {
    public static ArrayList<String> dataCategory() throws IOException {
        ArrayList<String> list = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("\\Программа учета расходов\\Spending\\src\\main\\resources\\categories.dat");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "Cp1251"));
            String str;
            list = new ArrayList<>();
            while ((str = reader.readLine()) != null) {
                if (!str.isEmpty()) {
                    list.add(str);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void addCategory(Stage myStage){
        String nameNewCategory;
        final TextField newCategory = new TextField();
        newCategory.setPrefColumnCount(20);   //установка ширины поля
        newCategory.setPromptText("Введите название новой категории"); //установка текста-приглашения
        Button buttonAdd = new Button("Добавить");
        Button buttonCANCEL = new Button("Отмена");

        FlowPane root1 = new FlowPane(20, 20);   //Создание корневого узла
        root1.setAlignment(Pos.CENTER);
        Scene myScene1 = new Scene(root1, 800, 500);  // Создать сцену
        myStage.setScene(myScene1);
        root1.getChildren().addAll(newCategory, buttonAdd, buttonCANCEL);

        buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Действия при нажатии кнопки Добавить
            }
        });

        buttonCANCEL.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Действия при нажатии кнопки Отмена
            }
        });

    }
}
