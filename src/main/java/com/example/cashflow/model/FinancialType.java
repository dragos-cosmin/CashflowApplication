package com.example.cashflow.model;

public enum FinancialType {

    PAYMENT("payment"),
    ENCASHMENT("encashment");;

    private String type;

    FinancialType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
