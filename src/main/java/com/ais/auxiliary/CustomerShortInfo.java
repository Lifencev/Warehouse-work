package com.ais.auxiliary;

public class CustomerShortInfo {

    private final String cardNumber;
    private final String surname;
    private final String name;

    public CustomerShortInfo(String cardNumber, String surname, String name) {
        this.cardNumber = cardNumber;
        this.surname = surname;
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }
}
