package com.utility;

import com.service.ExcelService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.model.GeneralHeader.*;
import static com.model.InstallmentHeader.*;


public class PaymentOperations extends ExcelService {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private Date date = new Date();
    private double rest;

    public PaymentOperations() {

    }


    public void lowerPaymentAmount(Sheet sheet, int rowNumber, double paymentAmount) {
        Row row = sheet.getRow(rowNumber);
        double expectedAmount = row.getCell(EXPECTED_AMOUNT.ordinal())
                .getNumericCellValue();

        row.createCell(ACTUAL_DATE.ordinal())
                .setCellValue(dateFormat.format(date));

        row.createCell(ACTUAL_AMOUNT.ordinal())
                .setCellValue(paymentAmount);

        //rest = row.getCell(EXPECTED_AMOUNT.ordinal()).getNumericCellValue() - paymentAmount;
        rest = row.getCell(5).getNumericCellValue() - paymentAmount;


        row.getCell(9)
                .setCellValue(expectedAmount + rest);

    }

    public void equalPaymentAmount(Sheet sheet, int rowNumber, double paymentAmount, int installmentCount) {
        Row row = sheet.getRow(rowNumber);

        row.createCell(ACTUAL_DATE.ordinal())
                .setCellValue(dateFormat.format(date));

        row.createCell(ACTUAL_AMOUNT.ordinal())
                .setCellValue(paymentAmount);

        row.createCell(ACTUAL_INSTALLMENT_NUMBER.ordinal())
                .setCellValue(installmentCount += 1);
    }

    public void greaterPaymentAmount(Sheet sheet, int rowNumber, double paymentAmount) {
        Row row = sheet.getRow(rowNumber);
        double expectedAmount = row.getCell(9)
                .getNumericCellValue();

        row.createCell(ACTUAL_DATE.ordinal())
                .setCellValue(dateFormat.format(date));

        row.createCell(ACTUAL_AMOUNT.ordinal())
                .setCellValue(paymentAmount);

        rest = row.getCell(5).getNumericCellValue() - paymentAmount;

        row.getCell(9)
                .setCellValue(expectedAmount + rest);
    }
}
