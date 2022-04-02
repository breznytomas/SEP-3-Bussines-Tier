package com.sep.carsharingbusiness.graphQLServices;

import com.sep.carsharingbusiness.model.Customer;

import java.io.IOException;

public interface ICustomerService {
    Customer getCustomer(String cpr) throws IOException, InterruptedException;
    Customer addCustomer(Customer customer) throws IOException, InterruptedException;
    Customer updateCustomer(Customer customer) throws IOException, InterruptedException;
    boolean removeCustomer(String cpr) throws IOException, InterruptedException;
}
