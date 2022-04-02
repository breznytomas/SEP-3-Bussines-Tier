package com.sep.carsharingbusiness.restControllers;


import com.google.gson.Gson;
import com.sep.carsharingbusiness.log.Log;
import com.sep.carsharingbusiness.logic.IPaymentLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
public class MobilePayController {
    private final IPaymentLogic paymentLogic;

    @Autowired
    public MobilePayController(IPaymentLogic paymentLogic) {
        this.paymentLogic = paymentLogic;
    }

    // TODO: 11.12.2021 by Ion - Send a payment obj with totalPrice, sender and receiver & change to Post
    @GetMapping(value = "/mobilepay")
    public synchronized String getPaymentID( @RequestParam(value = "amount") double amount) {
        try {
            Log.addLog("|restControllers/MobilePayController.getPaymentID| : Request : TotalPayment: " + amount + " dkk");
            return paymentLogic.getPaymentID(amount);
        }
        catch (Exception e) {
            Log.addLog("|restControllers/ListingController.getListingsByVehicle| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @GetMapping(value = "/mobilepay/verify")
    public synchronized boolean verifyPayment(String paymentId) {
        try {
            Log.addLog("|restControllers/MobilePayController.verifyPayment|");
            return paymentLogic.verifyPayment(paymentId);
        }
        catch (InterruptedException e) {
            Log.addLog("|restControllers/ListingController.getListingsByVehicle| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

}
