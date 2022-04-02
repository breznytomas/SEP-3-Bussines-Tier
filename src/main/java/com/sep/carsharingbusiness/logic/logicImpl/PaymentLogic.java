package com.sep.carsharingbusiness.logic.logicImpl;

import com.sep.carsharingbusiness.logic.IPaymentLogic;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
public class PaymentLogic implements IPaymentLogic {

    //in the future here will be a connection to MobilePay API, from which the payment address will be obtained and
    //also the final price will be sent to mobile pay to verify that the received amount is the correct amount

    @SessionScope
    public String getPaymentID(double amount) {
        return "123456";
    }

    @SessionScope
    public boolean verifyPayment(String paymentID) throws InterruptedException {
        Thread.sleep(4500);
        return true;
    }
}
