package excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *   Excel解析器
 */
public class ExcelReader {

    public static void main(String[] args) {
        Formatter formatter = new QuoteFormatter();
        String result = readExcelColumnAsSqlParam("file.xlsx", ",", 1, formatter);
        System.out.println(result);
    }

    /**
     * 场景:读取excel的某一列,作为SQL参数
     *
     * @param filePath
     * @param separator
     * @param columnNumber
     * @param formatter
     * @return
     */
    public static String readExcelColumnAsSqlParam(String filePath, String separator, Integer columnNumber, Formatter formatter) {
        InputStream is;
        Workbook wb = null;
        try {
            String extString = filePath.substring(filePath.lastIndexOf("."));
            is = new FileInputStream(filePath);
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {
                wb = new XSSFWorkbook(is);
            } else {
                return null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        //获取行数
        int rowNum = sheet.getPhysicalNumberOfRows();
        //int columnNum = sheet.getRow(0).getPhysicalNumberOfCells();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            stringBuilder.append(formatter.format(parseCellValue(row.getCell(columnNumber))));
            if (i != rowNum - 1) {
                stringBuilder.append(separator);
            }
        }
        return stringBuilder.toString();
    }

    private static String parseCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getRichStringCellValue().getString().trim();
            default:
                return "";
        }
    }
}


