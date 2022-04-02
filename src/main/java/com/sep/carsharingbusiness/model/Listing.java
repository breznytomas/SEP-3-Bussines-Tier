package com.sep.carsharingbusiness.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Listing {
    private int id;
    private LocalDateTime listedDate;
    private BigDecimal price;
    private String location;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    public Vehicle vehicle;

    public Listing(int id, LocalDateTime listedDate, BigDecimal price, String location, LocalDateTime dateFrom, LocalDateTime dateTo, Vehicle vehicle) {
        this.id = id;
        this.listedDate = listedDate;
        this.price = price;
        this.location = location;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.vehicle = vehicle;
    }

    public Listing(LocalDateTime listedDate, BigDecimal price, String location, LocalDateTime dateFrom, LocalDateTime dateTo, Vehicle vehicle) {
        this.listedDate = listedDate;
        this.price = price;
        this.location = location;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.vehicle = vehicle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getListedDate() {
        return listedDate;
    }

    public void setListedDate(LocalDateTime listedDate) {
        this.listedDate = listedDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
