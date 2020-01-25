package com.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExcelService {


    public final static String PROJECT_PATH = System.getProperty("user.dir");
    public final static String EXCEL_PATH = PROJECT_PATH + "/Excel";
    public static final String FILENAME = EXCEL_PATH + "/Payment_schedule.xlsx";


    public CellStyle getCellStyle() {
        return cellStyle;
    }

    protected CellStyle cellStyle;

    protected void headerSetup(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        this.cellStyle = headerCellStyle;
    }

    public static int getUserRow(String email) throws IOException, InvalidFormatException {

        int i = 1;
        Path path = Paths.get(EXCEL_PATH + "/Payment_schedule.xlsx");

        InputStream inp = new FileInputStream(String.valueOf(path));
        Workbook workbook = WorkbookFactory.create(inp);
        Sheet sheet = workbook.getSheet("User");


        for (i = 1; i <= sheet.getLastRowNum(); i++) {
            if (email.contains(sheet.getRow(i).getCell(1).getStringCellValue())) {
                break;
            }
            //TODO what if not found email (else).
        }


        return i;
    }

}
