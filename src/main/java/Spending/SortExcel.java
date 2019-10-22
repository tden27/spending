package Spending;

import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.Comparator;
import java.util.List;

public class SortExcel {
    public static void sortSheet(Workbook workbook, Sheet sheet) {
        // скопировать все строки в темп
        List<Row> rows = Lists.newArrayList(sheet.rowIterator());
        //сортировать строки в темп
        rows.sort(Comparator.comparing(cells -> cells.getCell(0).getStringCellValue()));
        // удалить все строки с листа
        removeAllRows(sheet);
        // создавать новые строки со значениями отсортированных строк из temp
        for (int i = 0; i < rows.size(); i++) {
            Row newRow = sheet.createRow(i);
            Row sourceRow = rows.get(i);
            // Перебрать исходные столбцы для добавления в новую строку
            for (int j = 0; j < sourceRow.getLastCellNum(); j++) {
                // Возьмите копию старой / новой ячейки
                Cell oldCell = sourceRow.getCell(j);
                Cell newCell = newRow.createCell(j);

                // Если старая ячейка пуста, переходите к следующей ячейке
                if (oldCell == null) {
                    newCell = null;
                    continue;
                }

                // Скопировать стиль из старой ячейки и применить к новой ячейке
                CellStyle newCellStyle = workbook.createCellStyle();
                newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
                newCell.setCellStyle(newCellStyle);

                // Если есть комментарий к ячейке, скопируйте
                if (oldCell.getCellComment() != null) {
                    newCell.setCellComment(oldCell.getCellComment());
                }

                // Если есть гиперссылка на ячейку, скопируйте
                if (oldCell.getHyperlink() != null) {
                    newCell.setHyperlink(oldCell.getHyperlink());
                }

                // Установите тип данных ячейки
                newCell.setCellType(oldCell.getCellType());

                // Установите значение данных ячейки
                switch (oldCell.getCellType()) {
                    case BLANK:
                        newCell.setCellValue(oldCell.getStringCellValue());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(oldCell.getBooleanCellValue());
                        break;
                    case ERROR:
                        newCell.setCellErrorValue(oldCell.getErrorCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellFormula(oldCell.getCellFormula());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(oldCell.getNumericCellValue());
                        break;
                    case STRING:
                        newCell.setCellValue(oldCell.getRichStringCellValue());
                        break;
                }
            }

            // Если в исходной строке есть объединенные области, скопируйте в новую строку
            for (int j = 0; j < sheet.getNumMergedRegions(); j++) {
                CellRangeAddress cellRangeAddress = sheet.getMergedRegion(j);
                if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                    CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                            (newRow.getRowNum() +
                                    (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
                                    )),
                            cellRangeAddress.getFirstColumn(),
                            cellRangeAddress.getLastColumn());
                    sheet.addMergedRegion(newCellRangeAddress);
                }
            }
        }

    }

    private static void removeAllRows(Sheet sheet) {
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            sheet.removeRow(sheet.getRow(i));
        }
    }
}
