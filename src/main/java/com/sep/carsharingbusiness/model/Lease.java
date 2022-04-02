package com.sep.carsharingbusiness.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Lease {
    private int id;
    private LocalDateTime leasedFrom;
    private LocalDateTime leasedTo;
    private boolean canceled;
    private BigDecimal totalPrice;
    public Listing listing;
    public Customer customer;

    public Lease(int id, LocalDateTime leasedFrom, LocalDateTime leasedTo, boolean canceled, BigDecimal totalPrice, Listing listing, Customer customer) {
        this.id = id;
        this.leasedFrom = leasedFrom;
        this.leasedTo = leasedTo;
        this.canceled = canceled;
        this.listing = listing;
        this.customer = customer;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getLeasedFrom() {
        return leasedFrom;
    }

    public void setLeasedFrom(LocalDateTime leasedFrom) {
        this.leasedFrom = leasedFrom;
    }

    public LocalDateTime getLeasedTo() {
        return leasedTo;
    }

    public void setLeasedTo(LocalDateTime leasedTo) {
        this.leasedTo = leasedTo;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
