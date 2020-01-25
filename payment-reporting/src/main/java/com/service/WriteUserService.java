package com.service;

import com.model.Installment;
import com.model.User;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class WriteUserService extends ExcelService {

    private static String[] userColumns = {"Name", "E-mail", "Actual total amount", "Expected total amount"};
    private static String[] installmentColumns = {"Due date", "", "Actual amount", "Expected amount"};


    //Creating schedule for one user.
    public void saveUserToExcel(User user) throws IOException {

        Files.createDirectories(Paths.get(EXCEL_PATH));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("User");
        String fileName = EXCEL_PATH + "/" + user.getName() + "_payment_schedule.xlsx";

        addUserPart(workbook, sheet, user);
        addInstallmentPart(workbook, sheet, user.getPaymentSchedule().getInstallmentList());

        for (int i = 0; i < userColumns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }

    private void addUserPart(Workbook workbook, Sheet sheet, User user) {

        Row headerRow = sheet.createRow(0);

        for (int i = 0; i < userColumns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(userColumns[i]);
            cell.setCellStyle(cellStyle);
        }

        Row row = sheet.createRow(1);

        row.createCell(0)
                .setCellValue(user.getName());

        row.createCell(1)
                .setCellValue(user.getEmail());

        row.createCell(2)
                .setCellValue(user.getPaymentSchedule().getActualTotalAmount());

        row.createCell(3)
                .setCellValue(user.getPaymentSchedule().getExpectedTotalAmount());
    }

    private void addInstallmentPart(Workbook workbook, Sheet sheet, List<Installment> installmentList) {

        Row headerRow = sheet.createRow(4);

        for (int i = 0; i < installmentColumns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(installmentColumns[i]);
            cell.setCellStyle(cellStyle);
        }


        int rowNum = 5;

        for (Installment installment : installmentList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0)
                    .setCellValue(installment.getDueDate().toString());

            row.createCell(2)
                    .setCellValue(installment.getActualAmount());

            row.createCell(3)
                    .setCellValue(installment.getExpectedAmount());

        }
    }

}
