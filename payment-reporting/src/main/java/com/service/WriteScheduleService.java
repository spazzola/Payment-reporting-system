package com.service;

import com.model.GeneralHeader;
import com.model.Installment;
import com.model.InstallmentHeader;
import com.model.User;
import com.utility.DateUtility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.model.GeneralHeader.*;
import static com.model.InstallmentHeader.*;


public class WriteScheduleService extends ExcelService {

    //Creating many users in one schedule.
    public void generateSchedule(User user) throws IOException, InvalidFormatException {

        Files.createDirectories(Paths.get(EXCEL_PATH));

        Sheet sheet;

        Path path = Paths.get(FILENAME);

        if (Files.exists(path)) {
            InputStream inp = new FileInputStream(FILENAME);
            Workbook workbook = WorkbookFactory.create(inp);

            sheet = workbook.getSheet("User");

            addStaff(workbook, sheet, user);

            FileOutputStream fileOut = new FileOutputStream(FILENAME);
            workbook.write(fileOut);
            fileOut.close();

        } else {
            Workbook workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("User");

            addStaff(workbook, sheet, user);
            autosizeColumns(sheet);

            FileOutputStream fileOut = new FileOutputStream(FILENAME);
            workbook.write(fileOut);
            fileOut.close();

            // Closing the workbook
            workbook.close();
        }
    }

    public void updateSchedule(User user) throws IOException, InvalidFormatException {

        int userRow = ExcelService.getUserRow(user.getEmail());

        //Path path = Paths.get(FILENAME);
        InputStream inp = new FileInputStream(FILENAME);
        Workbook workbook = WorkbookFactory.create(inp);
        Sheet sheet = workbook.getSheet("User");

        Row row = sheet.getRow(userRow);

        row.createCell(ACTUAL_INSTALLMENT_NUMBER.ordinal())
                .setCellValue(user.getPaymentSchedule().getActualInstallment());

        row.createCell(ACTUAL_TOTAL_AMOUNT.ordinal())
                .setCellValue(user.getPaymentSchedule().getActualTotalAmount());

        List<Installment> installmentList = user.getPaymentSchedule().getInstallmentList();

        for (int i = 0; i < installmentList.size(); i++) {
            Installment installment = installmentList.get(i);
            double actualAmount = installment.getActualAmount();

            System.out.println(installment.getActualAmount());

            row.createCell(ACTUAL_AMOUNT.getCellNo() + (i * 4))
                    .setCellValue(actualAmount);
        }

        FileOutputStream fileOut = new FileOutputStream(FILENAME);
        workbook.write(fileOut);
        fileOut.close();

        workbook.close();

    }

    private void autosizeColumns(Sheet sheet) {
        for (int i = 0; i < GeneralHeader.values().length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void addStaff(Workbook workbook, Sheet sheet, User user) {
        headerSetup(workbook);
        addGeneralHeader(sheet);
        addInstallmentHeader(sheet, user);
        addUserData(sheet, user);
        addInstallmentData(sheet, user.getPaymentSchedule().getInstallmentList());
    }

    private void addGeneralHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        GeneralHeader[] headers = GeneralHeader.values();
        for (int i = 0; i < GeneralHeader.values().length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i].getName());
            cell.setCellStyle(cellStyle);
        }
    }

    private void addInstallmentHeader(Sheet sheet, User user) {

        int startedCell = GeneralHeader.values().length;
        int j = user.getPaymentSchedule().getInstallmentList().size();
        int columnsPerInstallment = InstallmentHeader.values().length;
        String[] paymentHeader = new String[columnsPerInstallment * j];
        int number = 1;

        for (int i = 0; i < paymentHeader.length; i += columnsPerInstallment) {
            paymentHeader[i] = EXPECTED_DATE.getName() + number;
            paymentHeader[i + 1] = EXPECTED_AMOUNT.getName() + number;
            paymentHeader[i + 2] = ACTUAL_DATE.getName() + number;
            paymentHeader[i + 3] = ACTUAL_AMOUNT.getName() + number;
            number += 1;
        }

        Row headerRow = sheet.getRow(0);
        //TODO why not to fill Cell immediately instead of filling paymentHeader
        for (int i = 0; i < columnsPerInstallment + startedCell + 2; i++) {
            Cell cell = headerRow.createCell(startedCell + i);
            cell.setCellValue(paymentHeader[i]);
            cell.setCellStyle(cellStyle);
            sheet.autoSizeColumn(i);
        }
    }

    private void addUserData(Sheet sheet, User user) {

        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        row.createCell(NAME.ordinal())
                .setCellValue(user.getName());

        row.createCell(EMAIL.ordinal())
                .setCellValue(user.getEmail());

        row.createCell(EXP_INSTALLMENT_NUMBER.ordinal())
                .setCellValue(user.getPaymentSchedule().getPaymentScheduleInfo().getNumberOfInstallments());

        row.createCell(ACTUAL_INSTALLMENT_NUMBER.ordinal())
                .setCellValue(0);

        row.createCell(EXPECTED_TOTAL_AMOUNT.ordinal())
                .setCellValue(user.getPaymentSchedule().getExpectedTotalAmount());

        row.createCell(ACTUAL_TOTAL_AMOUNT.ordinal())
                .setCellValue(0);

    }

    private void addInstallmentData(Sheet sheet, List<Installment> installmentList) {
        Row row = sheet.getRow((sheet.getLastRowNum()));

        int installmentCellIndex = GeneralHeader.values().length;
        for (Installment installment : installmentList) {
            row.createCell(installmentCellIndex)
                    .setCellValue(DateUtility.toString(installment.getDueDate()));

            row.createCell(installmentCellIndex + 1)
                    .setCellValue(installment.getExpectedAmount());

            row.createCell(installmentCellIndex + 2)
                    .setCellValue("");

            row.createCell(installmentCellIndex + 3)
                    .setCellValue(installment.getActualAmount());
            installmentCellIndex += 4;
        }

    }

}

