package com.ais.model;

import java.sql.Timestamp;

public class Check {

    private String checkNumber;
    private final String idEmployee;
    private final String cardNumber;
    private final Timestamp printDate;
    private final double sumTotal;
    private final double vat;

    public Check(String checkNumber, String idEmployee, String cardNumber, Timestamp printDate, double sumTotal, double vat) {
        this.checkNumber = checkNumber;
        this.idEmployee = idEmployee;
        this.cardNumber = cardNumber;
        this.printDate = printDate;
        this.sumTotal = sumTotal;
        this.vat = vat;
    }

    public Check(String idEmployee, String cardNumber, Timestamp printDate, double sumTotal, double vat) {
        this.idEmployee = idEmployee;
        this.cardNumber = cardNumber;
        this.printDate = printDate;
        this.sumTotal = sumTotal;
        this.vat = vat;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Timestamp getPrintDate() {
        return printDate;
    }

    public double getSumTotal() {
        return sumTotal;
    }

    public double getVat() {
        return vat;
    }
}