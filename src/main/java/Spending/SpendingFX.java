package Spending;

import javafx.application.*;
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

        // Создание файла Excel если он отсутствует
        if (!new File("\\Программа учета расходов\\Spending\\Spending.xls").isFile()) OutToExcel.writeIntoExcel();

        FlowPane root = new FlowPane(20, 20);   //Создание корневого узла
        root.setAlignment(Pos.CENTER); // Центрировать элементы управления на сцене
        Scene myScene = new Scene(root, 800, 500);  // Создать сцену
        myStage.setTitle("Мои расходы");    // Задать заголовок окна приложения
        myStage.setScene(myScene);  // Установить сцену на платформе

        // Создать объект типа ObservableList для списка
        ObservableList<String> categoriesType = FXCollections.observableArrayList(Transaction.categories);
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
            if (newVal != null)
                numCategories = Transaction.categories.indexOf(newVal);
        });

        // Действия при нажатии кнопки OK
        buttonOK.setOnAction(event -> {
            // Действия при нажатии кнопки OK
            try {
                File file = new File("\\Программа учета расходов\\Spending\\Spending.xls");
                FileInputStream inputStream = new FileInputStream(file);
                HSSFWorkbook myExcelBook = new HSSFWorkbook(inputStream);
                HSSFSheet myExcelSheet = myExcelBook.getSheet("Расходы");
                int numRow = myExcelSheet.getLastRowNum(); // Определяем последнюю использованную строку
                numRow++;

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
                    OutToExcel.createSheetHeader(myExcelBook, myExcelSheet, numRow, transaction);
                    //SortExcel.sortSheet(myExcelBook, myExcelSheet);
                    root.getChildren().removeAll(label1, label2);
                    lvCategories.setValue("Выберите категорию расходов");
                    summa.clear();
                    datePicker.setValue(LocalDate.now());
                    comment.clear();
                }
                inputStream.close();
                FileOutputStream outputStream = new FileOutputStream(file);
                myExcelBook.write(outputStream);
                outputStream.close();
                root.getChildren().remove(label3);
            }
            catch (FileNotFoundException e){
                System.out.println("File not found" + e);
            }
            catch (NumberFormatException e){
                label3.setText("В поле суммы должно быть числовое значение!");
                summa.clear();
                root.getChildren().remove(label3);
                root.getChildren().add(label3);
            }
            catch (IOException e){
                System.out.println("Возникло исключение" + e);
            }
        });

        // Действия при нажатии кнопки +
        buttonPlusCategory.setOnAction(event -> {
            // Действия при нажатии кнопки +
            DataCategories.addCategory(myStage);
        });

        // Действия при нажатии кнопки CANCEL
        buttonCANCEL.setOnAction(event -> {
            // Действия при нажатии кнопки CANCEL
            summa.clear();
            comment.clear();
        });
    }
}