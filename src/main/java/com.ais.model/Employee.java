package com.ais.model;

import java.sql.Date;

public class Employee {

    private String idEmployee;
    private final String EmplSurname;
    private final String EmplName;
    private final String EmplPatronymic;
    private final String EmplRole;
    private final double salary;
    private final Date dateOfBirth;
    private final Date dateOfStart;
    private final String phoneNumber;
    private final String city;
    private final String street;
    private final String zipCode;

    public Employee(String idEmployee, String emplSurname, String emplName, String emplPatronymic, String emplRole,
                    double salary, Date dateOfBirth, Date dateOfStart, String phoneNumber, String city, String street, String zipCode) {
        this.idEmployee = idEmployee;
        EmplSurname = emplSurname;
        EmplName = emplName;
        EmplPatronymic = emplPatronymic;
        EmplRole = emplRole;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.dateOfStart = dateOfStart;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public Employee(String emplSurname, String emplName, String emplPatronymic, String emplRole, double salary,
                    Date dateOfBirth, Date dateOfStart, String phoneNumber, String city, String street, String zipCode) {
        EmplSurname = emplSurname;
        EmplName = emplName;
        EmplPatronymic = emplPatronymic;
        EmplRole = emplRole;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
        this.dateOfStart = dateOfStart;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.zipCode = zipCode;
    }

    public String getIdEmployee() {
        return idEmployee;
    }

    public String getEmplSurname() {
        return EmplSurname;
    }

    public String getEmplName() {
        return EmplName;
    }

    public String getEmplPatronymic() {
        return EmplPatronymic;
    }

    public String getEmplRole() {
        return EmplRole;
    }

    public double getSalary() {
        return salary;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getDateOfStart() {
        return dateOfStart;
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
}