package com.model;

import com.utility.Loger;


public class User {

    private String name;
    private String email;
    private PaymentSchedule paymentSchedule;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public PaymentSchedule getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(PaymentSchedule paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void printInstallments() {
        for (Installment e : paymentSchedule.getInstallmentList()) {
            Loger.printInfo(e.toString());
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", paymentSchedule=" + paymentSchedule +
                '}';
    }
}
