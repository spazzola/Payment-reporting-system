package com.model;

import com.service.ExcelService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
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
import java.util.List;

public class EmailValidation {

    public boolean validateEmail(String email) {

        if (email.contains("@") && email.contains(".")) {
            return true;
        } else {
            System.out.println("E-mail should constains '@' and '.' . Enter your e-mail again.");
            return false;
        }
    }

    //Checking for creating schedule
    public boolean isEmailExist(String email) throws IOException, InvalidFormatException {
        List<String> emailList = emailCollector();
        if (emailList == null) {
            return true;
        } else {
            for (String element : emailList) {
                if (element.equals(email)) {
                    System.out.println("This e-mail already exist, enter your e-mail again.");
                    return false;
                }
            }
            return true;
        }
    }

    //Checking for addPayment()
    public boolean isEmailExist2(String email) throws IOException, InvalidFormatException {
        List<String> emailList = emailCollector();
        if (emailList == null) {
            return false;
        } else {
            for (String element : emailList) {
                if (element.equals(email)) {
                    return true;
                }
            }
            System.out.println("User not found. Enter e-mail again.");
            return false;
        }
    }

    public List<String> emailCollector() throws IOException, InvalidFormatException {
        Path path = Paths.get(ExcelService.EXCEL_PATH + "/Payment_schedule.xlsx");

        if (Files.exists(path)) {
            InputStream inp = new FileInputStream(String.valueOf(path));
            Workbook workbook = WorkbookFactory.create(inp);
            Sheet sheet = workbook.getSheet("User");

            List<String> emailList = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                emailList.add(sheet.getRow(i).getCell(1).getStringCellValue());
            }
            return emailList;
        } else {
            return null;
        }
    }

}