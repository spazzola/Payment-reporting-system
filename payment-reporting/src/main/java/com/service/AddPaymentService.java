package com.service;

import com.model.GeneralHeader;
import com.model.Installment;
import com.model.InstallmentHeader;
import com.model.User;
import com.utility.PaymentOperations;
import com.utility.ScannerUtility;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.util.List;


import java.io.IOException;

public class AddPaymentService extends PaymentOperations {


    private ReadExcelService readExcelService;
    private WriteScheduleService writeScheduleService;


    public AddPaymentService(){
        readExcelService = new ReadExcelService();
        writeScheduleService = new WriteScheduleService();
    }

    public void addPayment() throws Exception {

        //zeskanowac maila od uzytkownika
        ScannerUtility scannerUtility = new ScannerUtility();

        String email = readExcelService.getUserMail();

        //wyrzucic obiekt usera
        User user = readExcelService.getUserByEmail(email);


        //zeskanowac hajs od uzytkownika
        System.out.println("Type payment amount: ");
        double paymentAmount = scannerUtility.scanDouble();


        //dodac ten hajs dodac do usera wyciagnietego metoda <- oddzielna metoda/serwis
        addPaymentToUser(user, paymentAmount);

        //gotowy user do zapisu

        //uzyj WriteJakisSerwis do update user w excelu

        writeScheduleService.updateSchedule(user);



    }

    //nowa metoda przyjmaca user i  kwote do zaplaty

    private void addPaymentToUser(User user, Double paymentAmount) throws IOException, InvalidFormatException {

        List<Installment> installments = user.getPaymentSchedule().getInstallmentList();

        //posortuj installemnty po dacie, teraz ufamy excelowi ze ma raty po kolei

        for (int i = 0; i < installments.size(); i++) {

            if(paymentAmount == 0.0) {
                return;
            }

            //TODO zrefaktorowac user.getPaymentSchedule
            Installment installment = installments.get(i);
            int installmentNumber = user.getPaymentSchedule().getActualInstallment();
            double expectedAmount = installment.getExpectedAmount();
            double actualAmount = installment.getActualAmount();

            if (actualAmount < expectedAmount) {
                double rest = expectedAmount - actualAmount;

                if (paymentAmount > rest) {
                    actualAmount = actualAmount + rest;
                    paymentAmount = paymentAmount - rest;
                    installment.setActualAmount(actualAmount);
                    installmentNumber++;
                    user.getPaymentSchedule().setActualInstallment(installmentNumber);
                }else {
                    actualAmount = actualAmount + paymentAmount;
                    paymentAmount = 0.0;
                    installment.setActualAmount(actualAmount);
                }
            }

        }

        if (paymentAmount > 0) {
            System.out.println("Przeplaciles " + paymentAmount + " zl");
        }


    }

    private int getColNum(InstallmentHeader enumValue) {
        return enumValue.ordinal() + GeneralHeader.values().length;
    }



}








