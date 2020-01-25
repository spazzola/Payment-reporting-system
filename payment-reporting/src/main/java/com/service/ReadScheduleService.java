package com.service;

import com.model.PaymentScheduleInfo;
import com.utility.DateUtility;
import com.utility.Loger;
import com.utility.ScannerUtility;

import static com.utility.Loger.printInfo;


public class ReadScheduleService {


    protected PaymentScheduleInfo createPaymentScheduleInfo() {

        ScannerUtility scannerUtility = new ScannerUtility();
        PaymentScheduleInfo paymentScheduleInfo = new PaymentScheduleInfo();

        System.out.println("Number of installments: ");
        paymentScheduleInfo.setNumberOfInstallments(scannerUtility.scanInteger());

        System.out.println("Installment amount: ");
        paymentScheduleInfo.setInstallmentAmount(scannerUtility.scanDouble());

        printInfo("Date of first installment: (dd-MM-yyyy)");
        String dateString = scannerUtility.scanString();
        paymentScheduleInfo.setFirstDueDate(DateUtility.toDate(dateString));

        Loger.printInfo("Created PaymentScheduleInfo: " + paymentScheduleInfo);
        return paymentScheduleInfo;
    }

}