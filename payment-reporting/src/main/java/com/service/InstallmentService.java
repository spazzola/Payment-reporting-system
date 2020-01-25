package com.service;

import com.model.Installment;
import com.model.PaymentScheduleInfo;
import com.utility.DateUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstallmentService {

    public List<Installment> generateInstallments(PaymentScheduleInfo paymentScheduleInfo) {
        List<Installment> listOfInstallments = new ArrayList<>();

        Date dueDate = paymentScheduleInfo.getFirstDueDate();
        int numberOfInstallments = paymentScheduleInfo.getNumberOfInstallments();
        double installmentAmount = paymentScheduleInfo.getInstallmentAmount();

        for (int i = 0; i < numberOfInstallments; i++) {
            Installment installment = new Installment();
            installment.setDueDate(dueDate);
            installment.setExpectedAmount(installmentAmount);
            dueDate = DateUtility.incrementMonth(dueDate);
            listOfInstallments.add(installment);
        }

        return listOfInstallments;
    }

}
