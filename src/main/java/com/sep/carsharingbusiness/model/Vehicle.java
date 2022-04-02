package com.sep.carsharingbusiness.model;

public class Vehicle {
    private String licenseNo;
    private String brand;
    private String model;
    private String type;
    private String transmission;
    private String fuelType;
    private int seats;
    private int manufactureYear;
    private double mileage;

    private boolean isApproved;

    public Customer owner;

    public Vehicle(String licenseNo, String brand, String model, String type, String transmission, String fuelType, int seats, int manufactureYear, double mileage, boolean isApproved, Customer owner) {
        this.licenseNo = licenseNo;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.seats = seats;
        this.manufactureYear = manufactureYear;
        this.mileage = mileage;
        this.isApproved = isApproved;
        this.owner = owner;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}
