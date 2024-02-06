package com.ais.auxiliary;

public class EmployeeShortInfo {

    private final String id;
    private final String surname;
    private final String name;
    private final String city;
    private final double sumTotal;

    public EmployeeShortInfo(String id, String surname, String name, String city, double sumTotal) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.city = city;
        this.sumTotal = sumTotal;
    }

    public String getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public double getSumTotal() {
        return sumTotal;
    }
}
