package com.service;

import com.model.*;
import com.utility.Loger;
import com.utility.ScannerUtility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ReadUserService extends ExcelService {

    private EmailValidation emailValidation = new EmailValidation();


    public ReadUserService() {

    }

    public User createUser() throws IOException, InvalidFormatException {
        ScannerUtility scannerUtility = new ScannerUtility();
        User user = new User();

        String name;
        String email;

        System.out.println("Enter your following data:");

        do {
            System.out.println("\nEnter your e-mail:");
            email = scannerUtility.scanString();
        } while (!emailValidation.validateEmail(email) || !emailValidation.isEmailExist(email));
        user.setEmail(email);

        System.out.println("Enter your name: ");
        name = scannerUtility.scanString();
        user.setName(name);

        Loger.printInfo("User created. " + user);

        return user;
    }

}
