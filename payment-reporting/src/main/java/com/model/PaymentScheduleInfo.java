package com.model;

import java.util.Date;

public class PaymentScheduleInfo {

    private Date firstDueDate;
    private int numberOfInstallments;
    private double installmentAmount;

    public PaymentScheduleInfo() {

    }

    public void setFirstDueDate(Date firstDueDate) {
        this.firstDueDate = firstDueDate;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public void setNumberOfInstallments(int numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public Date getFirstDueDate() {
        return firstDueDate;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    @Override
    public String toString() {
        return "PaymentScheduleInfo{" +
                "firstDueDate=" + firstDueDate +
                ", numberOfInstallments=" + numberOfInstallments +
                ", installmentAmount=" + installmentAmount +
                '}';
    }
}