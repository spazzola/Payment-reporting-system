package com.model;

import java.util.ArrayList;
import java.util.List;

public class PaymentSchedule {

    private PaymentScheduleInfo paymentScheduleInfo;
    private int actualInstallment;
    private double actualTotalAmount;
    private double expectedTotalAmount;
    private List<Installment> installmentList = new ArrayList<>();


    public PaymentSchedule() {

    }

    public List<Installment> getInstallmentList() {
        return installmentList;
    }

    public void setPaymentScheduleInfo(PaymentScheduleInfo paymentScheduleInfo) {
        this.paymentScheduleInfo = paymentScheduleInfo;
    }

    public PaymentScheduleInfo getPaymentScheduleInfo() {
        return paymentScheduleInfo;
    }

    public int getActualInstallment() {
        return actualInstallment;
    }

    public double getExpectedTotalAmount() {
        return expectedTotalAmount;
    }

    public double getActualTotalAmount() {
        double sum = 0;

        for (Installment installment : installmentList) {
            sum += installment.getActualAmount();
        }
        return sum;
    }

    public void setActualInstallment(int actualInstallment) {
        this.actualInstallment = actualInstallment;
    }

    public void setActualTotalAmount(double actualTotalAmount) {
        this.actualTotalAmount = actualTotalAmount;
    }

    public void setExpectedTotalAmount(double expectedTotalAmount) {
        this.expectedTotalAmount = expectedTotalAmount;
    }

    public void setInstallmentList(List<Installment> installmentList) {
        this.installmentList = installmentList;
    }

    @Override
    public String toString() {
        return "PaymentSchedule{" +
                "paymentScheduleInfo=" + paymentScheduleInfo +
                ", actualTotalAmount=" + actualTotalAmount +
                ", expectedTotalAmount=" + expectedTotalAmount +
                ", installmentList=" + installmentList +
                '}';
    }
}
