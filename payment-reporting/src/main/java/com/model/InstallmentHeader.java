package com.model;

public enum InstallmentHeader {
    EXPECTED_DATE("Exp. date ", 6),
    EXPECTED_AMOUNT("Exp. amount ", 7),
    ACTUAL_DATE("Actual date ", 8),
    ACTUAL_AMOUNT("Actual amount ", 9);

    private String name;
    private int cellNo;

    InstallmentHeader(String name, int cellNo) {
        this.name = name;
        this.cellNo = cellNo;
    }

    public String getName() {
        return name;
    }

    public int getCellNo() {
        return cellNo;
    }
}
