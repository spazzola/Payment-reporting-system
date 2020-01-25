package com.service;

import com.model.User;
import com.utility.ScannerUtility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public class GeneralMenu {

    public static void runProgram() throws Exception {
        ScannerUtility scannerUtility = new ScannerUtility();
        int choice;

        System.out.println(System.getProperty("user.dir"));
        System.out.println("Choice one from the following options:");
        System.out.println("1. Create payment schedule for a user.");
        System.out.println("2. Add a payment.");

        choice = scannerUtility.scanInteger();
        User user;
        switch (choice) {
            case 1:
                CreateScheduleService createScheduleService = new CreateScheduleService();
                user = createScheduleService.createSchedule();
                WriteScheduleService writeScheduleService = new WriteScheduleService();
                writeScheduleService.generateSchedule(user);
                break;
            case 2:
                AddPaymentService addPaymentService = new AddPaymentService();
                addPaymentService.addPayment();
                break;
        }
    }

}
