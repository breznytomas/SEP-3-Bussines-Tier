package com.sep.carsharingbusiness.model;

import java.math.BigDecimal;

public class Payment {
    private int paymentID;
    private String senderPhone;
    private String receiverPhone;
    private BigDecimal amount;

    public Payment(int paymentID, String senderPhone, String receiverPhone, BigDecimal amount) {
        this.paymentID = paymentID;
        this.senderPhone = senderPhone;
        this.receiverPhone = receiverPhone;
        this.amount = amount;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
