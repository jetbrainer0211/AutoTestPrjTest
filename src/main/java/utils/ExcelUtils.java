package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {
    public static Object[][] getTableArray(String filePath, String sheetName, int startCol, int totalCol) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();
        int rowCount = sheet.getPhysicalNumberOfRows();

        // Ensure startCol and totalCol are within bounds
        Row firstRow = sheet.getRow(0);
        if (firstRow == null) {
            throw new IllegalArgumentException("Sheet is empty");
        }
        Object[][] data = new Object[rowCount - 1][totalCol];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                for (int j = 0; j < totalCol; j++) {
                    Cell cell = row.getCell(startCol + j);
                    data[i - 1][j] = (cell == null) ? "" : formatter.formatCellValue(cell);
                }
            }
        }
        workbook.close();
        fis.close();
        return data;
    }
}
