package com.service;

import com.model.EmailValidation;
import com.model.Installment;
import com.model.PaymentSchedule;
import com.model.User;
import com.utility.DateUtility;
import com.utility.ScannerUtility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.service.ExcelService.EXCEL_PATH;
import static com.service.ExcelService.FILENAME;
import static com.model.GeneralHeader.*;
import static com.model.InstallmentHeader.*;

public class ReadExcelService {

    private EmailValidation emailValidation = new EmailValidation();

    public User getUserByEmail(String email) throws Exception {

        Path path = Paths.get(FILENAME);

        if (!Files.exists(path)) {
            throw new Exception("no file");
        }

        if (!emailValidation.isEmailExist2(email)) {
            throw new Exception("no email");
        }

        InputStream inp = new FileInputStream(FILENAME);
        Workbook workbook = WorkbookFactory.create(inp);
        Sheet sheet = workbook.getSheet("User");

        int rowNum = ExcelService.getUserRow(email);

        Row row = sheet.getRow(rowNum);

        final User user = new User();

        user.setName(row.getCell(NAME.ordinal()).getStringCellValue());
        user.setEmail(row.getCell(EMAIL.ordinal()).getStringCellValue());


        final PaymentSchedule paymentSchedule = new PaymentSchedule();

        paymentSchedule.setExpectedTotalAmount(row.getCell(EXPECTED_TOTAL_AMOUNT.ordinal()).getNumericCellValue());
        paymentSchedule.setActualTotalAmount(row.getCell(ACTUAL_TOTAL_AMOUNT.ordinal()).getNumericCellValue());
        paymentSchedule.setActualInstallment((int)row.getCell(ACTUAL_INSTALLMENT_NUMBER.ordinal()).getNumericCellValue());

        final List<Installment> installmentList = new ArrayList<>();

        int numberOfInstallments = (int) row.getCell(EXP_INSTALLMENT_NUMBER.ordinal()).getNumericCellValue();

        for (int i = 0; i < numberOfInstallments; i++) {
            Installment installment = new Installment();

            String stringDueDate = row.getCell( EXPECTED_DATE.getCellNo() + (i * 4)).getStringCellValue();
            Date dueDate = DateUtility.toDate(stringDueDate);
            installment.setDueDate(dueDate);

            double expectedAmount = row.getCell(EXPECTED_AMOUNT.getCellNo() + (i * 4)).getNumericCellValue();
            installment.setExpectedAmount(expectedAmount);

            //installment.setActualDate(row.getCell(ACTUAL_DATE.getCellNo() + (i * 4)).getDateCellValue());

            double actualAmount = row.getCell(ACTUAL_AMOUNT.getCellNo() + (i * 4)).getNumericCellValue();
            installment.setActualAmount(actualAmount);

            installmentList.add(installment);
        }


        paymentSchedule.setInstallmentList(installmentList);

        user.setPaymentSchedule(paymentSchedule);

        return user;
    }

    public String getUserMail() throws IOException, InvalidFormatException {

        String email;
        ScannerUtility scannerUtility = new ScannerUtility();
        EmailValidation emailValidation = new EmailValidation();

        do {
            System.out.println("Write user e-mail:");
            email = scannerUtility.scanString();
        } while (!emailValidation.validateEmail(email) || !emailValidation.isEmailExist2(email));

        return email;
    }

}
