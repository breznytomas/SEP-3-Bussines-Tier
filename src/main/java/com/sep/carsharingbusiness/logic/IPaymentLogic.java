package com.sep.carsharingbusiness.logic;

public interface IPaymentLogic {
    String getPaymentID(double amount);
    boolean verifyPayment(String paymentID) throws InterruptedException;
}
