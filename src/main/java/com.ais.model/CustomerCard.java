package com.ais.model;

public class CustomerCard {

    private String cardNumber;
    private final String custSurname;
    private final String custName;
    private final String custPatronymic;
    private final String phoneNumber;
    private final String city;
    private final String street;
    private final String zipCode;
    private final int percent;

    public CustomerCard(String cardNumber, String custSurname, String custName, String custPatronymic,
                        String phoneNumber, String city, String street, String zipCode, int percent) {
        this.cardNumber = cardNumber;
        this.custSurname = custSurname;
        this.custName = custName;
        this.custPatronymic = custPatronymic;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.percent = percent;
    }

    public CustomerCard(String custSurname, String custName, String custPatronymic,
                        String phoneNumber, String city, String street, String zipCode, int percent) {
        this.custSurname = custSurname;
        this.custName = custName;
        this.custPatronymic = custPatronymic;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
        this.percent = percent;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCustSurname() {
        return custSurname;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustPatronymic() {
        return custPatronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getPercent() {
        return percent;
    }
}