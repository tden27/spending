package Spending;

import Spending.model.DataCategories;
import Spending.model.Transaction;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.collections.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;

public class SpendingFX extends Application {

    private int numCategories;
    private String n = "Выберите категорию расходов";
    private double sum;
    private String commentary;
    private Transaction transaction;
    private Label label1 = new Label();
    private Label label2 = new Label();
    private Label label3 = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) {
        FlowPane root = new FlowPane(20, 20);   //Создание корневого узла
        root.setAlignment(Pos.CENTER); // Центрировать элементы управления на сцене
        Scene myScene = new Scene(root, 800, 500);  // Создать сцену
        myStage.setTitle("Мои расходы");    // Задать заголовок окна приложения
        myStage.setScene(myScene);  // Установить сцену на платформе

        // Создать объект типа ObservableList для списка
        ObservableList<String> categoriesType = FXCollections.observableArrayList(Transaction.categories.values()).sorted();
        final ComboBox<String> lvCategories = new ComboBox<>(categoriesType); // Создать список
        lvCategories.setValue("Выберите категорию расходов"); // Устанавливаем начальное значение

        // Получить модель выбора для списка
        SingleSelectionModel<String> lvSelModel = lvCategories.getSelectionModel();

        // Создание кнопки добавления категории
        Button buttonPlusCategory = new Button("+");

        // Создание поля для ввода суммы расхода
        final TextField summa = new TextField();
        summa.setPrefColumnCount(10);   //установка ширины поля
        summa.setPromptText("Введите сумму"); //установка текста-приглашения

        // Создание поля для выбора даты
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);

        // Создание поля для написания комментария
        final TextField comment = new TextField();
        comment.setPrefColumnCount(20);   //установка ширины поля
        comment.setPromptText("Напишите комментарий"); //установка текста-приглашения

        // Создание кнопок OK и CANCEL
        Button buttonOK = new Button("OK");
        Button buttonCANCEL = new Button("CANCEL");

        // Добавляем список и поле в граф сцены
        root.getChildren().addAll(lvCategories, buttonPlusCategory, summa, datePicker, comment, buttonOK, buttonCANCEL);

        // Отобразить платформу и ее сцену
        myStage.show();

        // Использовать слушатель для реагирования на изменения выделения внутри списка.
        lvSelModel.selectedItemProperty().addListener ((changed, oldVal, newVal) -> {
            // В переменную numCategories записываем номер выбранной категории
            if (newVal != null) {
                for (Map.Entry<Integer, String> entry : Transaction.categories.entrySet()) {
                    if (entry.getValue().equals(newVal)) numCategories = entry.getKey();
                }
            }
        });

        // Действия при нажатии кнопки OK
        buttonOK.setOnAction(event -> {
            // Действия при нажатии кнопки OK
            try {
                LocalDate dateOfTr = datePicker.getValue();

                if (lvCategories.getValue().equals(n)) {
                    label1.setText("Выберите категорию расходов");
                    root.getChildren().remove(label1);
                    root.getChildren().add(label1);
                }
                else if (summa.getText().isEmpty()) {
                    label2.setText("Введите сумму");
                    root.getChildren().removeAll(label1, label2);
                    root.getChildren().add(label2);
                }
                else {
                    sum = Double.parseDouble(summa.getText());
                    commentary = comment.getText();
                    transaction = new Transaction(numCategories, sum, dateOfTr, commentary);
                    OutToExcel.createSheetHeader(transaction);
                    OutToDB.addValueIntoDB(transaction);
                    //SortExcel.sortSheet(myExcelBook, myExcelSheet);
                    root.getChildren().removeAll(label1, label2);
                    lvCategories.setValue("Выберите категорию расходов");
                    summa.clear();
                    datePicker.setValue(LocalDate.now());
                    comment.clear();
                }
                root.getChildren().remove(label3);
            }
            catch (NumberFormatException e){
                label3.setText("В поле суммы должно быть числовое значение!");
                summa.clear();
                root.getChildren().remove(label3);
                root.getChildren().add(label3);
            }
        });

        // Действия при нажатии кнопки +
        buttonPlusCategory.setOnAction(event -> {
            // Действия при нажатии кнопки +
            final TextField newCategory = new TextField();
            newCategory.setPrefColumnCount(20);   //установка ширины поля
            newCategory.setPromptText("Введите название новой категории"); //установка текста-приглашения
            Button buttonAdd = new Button("Добавить");
            //Button buttonCANCEL = new Button("Отмена");

            FlowPane root1 = new FlowPane(20, 20);   //Создание корневого узла
            root1.setAlignment(Pos.CENTER);
            Scene myScene1 = new Scene(root1, 800, 500);  // Создать сцену
            myStage.setScene(myScene1);
            root1.getChildren().addAll(newCategory, buttonAdd, buttonCANCEL);

            // Действия при нажатии кнопки Добавить
            buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DataCategories.addCategory(newCategory.getText());
                    newCategory.clear();
                }
            });

            // Действия при нажатии кнопки Отмена
            buttonCANCEL.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    myStage.setScene(myScene);
                }
            });
        });

        // Действия при нажатии кнопки CANCEL
        buttonCANCEL.setOnAction(event -> {
            lvCategories.setValue("Выберите категорию расходов");
            summa.clear();
            datePicker.setValue(LocalDate.now());
            comment.clear();
            label1.setText("");
            label2.setText("");
            label3.setText("");
        });
    }
}