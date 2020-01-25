package com.model;

public enum GeneralHeader {
    NAME("Name"),
    EMAIL("Email"),
    EXP_INSTALLMENT_NUMBER("Exp. installment #"),
    ACTUAL_INSTALLMENT_NUMBER("Actual installment #"),
    EXPECTED_TOTAL_AMOUNT("Expected total amount"),
    ACTUAL_TOTAL_AMOUNT("Actual total amount");

    private String name;

    GeneralHeader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
