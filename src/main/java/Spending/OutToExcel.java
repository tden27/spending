package Spending;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

class OutToExcel {

    static void writeIntoExcel(){

        HSSFWorkbook workbook = new HSSFWorkbook(); // создание самого excel файла в памяти
        HSSFSheet sheet = workbook.createSheet("Расходы");  // создание листа с названием "Расходы"

        // создаем подписи к столбцам (это будет первая строчка в листе Excel файла)
        Row row = sheet.createRow(0);
        row.setHeightInPoints(30);

        row.createCell(0).setCellValue("Категория");
        row.getCell(0).setCellStyle(getStyleTitle(workbook));
        row.createCell(1).setCellValue("Сумма");
        row.getCell(1).setCellStyle(getStyleTitle(workbook));
        row.createCell(2).setCellValue("Дата");
        row.getCell(2).setCellStyle(getStyleTitle(workbook));
        row.createCell(3).setCellValue("Комментарии");
        row.getCell(3).setCellStyle(getStyleTitle(workbook));

        sheet.setColumnWidth(0, 3500);    // Меняем ширину столбца Категория
        sheet.setColumnWidth(1, 2000);    // Меняем ширину столбца Сумма
        sheet.setColumnWidth(2, 3500);    // Меняем ширину столбца Дата
        sheet.setColumnWidth(3, 5500);    // Меняем ширину столбца Комментарий

        // записываем созданный в памяти Excel документ в файл
        try (FileOutputStream out = new FileOutputStream(new File("\\Программа учета расходов\\Spending\\Spending.xls"))) {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Excel файл успешно создан!");
    }

    // заполнение строки (rowNum) определенного листа (sheet) данными  из dataModel
    static void createSheetHeader(HSSFWorkbook workbook, HSSFSheet sheet1, int rowNum1, Transaction dataModel) {
        Row row1 = sheet1.createRow(rowNum1);
        row1.setHeightInPoints(15);

        row1.createCell(0).setCellValue(dataModel.getCategory());
        row1.getCell(0).setCellStyle(getSampleStyle1(workbook));
        row1.createCell(1).setCellValue(dataModel.getSum());
        row1.getCell(1).setCellStyle(getSampleStyle2(workbook));
        row1.createCell(2).setCellValue(dataModel.getLocalDate());
        row1.getCell(2).setCellStyle(getSampleStyle2(workbook));
        row1.createCell(3).setCellValue(dataModel.getComment());
        row1.getCell(3).setCellStyle(getSampleStyle1(workbook));

        HSSFCreationHelper createHelper = workbook.getCreationHelper();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.MM.YYYY"));

        row1.createCell(5).setCellValue(new Date());
        row1.getCell(5).setCellStyle(cellStyle);

    }

    // Установка стиля1 для строки
    private static HSSFCellStyle getSampleStyle1(HSSFWorkbook workbook) {
        HSSFCellStyle style1=workbook.createCellStyle();
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        return style1;
    }

    // Установка стиля2 для строки
    private static HSSFCellStyle getSampleStyle2(HSSFWorkbook workbook) {
        HSSFCellStyle style2=workbook.createCellStyle();
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        return style2;
    }

    // Установка стиля для заголовка таблицы
    private static HSSFCellStyle getStyleTitle(HSSFWorkbook workbook) {
        HSSFCellStyle style1Title = workbook.createCellStyle();
        style1Title.setBorderBottom(BorderStyle.THIN);
        style1Title.setBorderLeft(BorderStyle.THIN);
        style1Title.setBorderRight(BorderStyle.THIN);
        style1Title.setBorderTop(BorderStyle.THIN);
        style1Title.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        style1Title.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont titleFont = workbook.createFont();
        titleFont.setBold(true);
        style1Title.setFont(titleFont);
        return style1Title;
    }
}
