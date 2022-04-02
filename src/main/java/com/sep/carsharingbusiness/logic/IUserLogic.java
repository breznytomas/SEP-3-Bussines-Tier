package com.sep.carsharingbusiness.logic;

import com.sep.carsharingbusiness.model.Account;
import com.sep.carsharingbusiness.model.Customer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface IUserLogic {
    Customer login(Account account) throws IOException, InterruptedException, IllegalAccessException, NoSuchAlgorithmException;
    Customer register(Account account) throws IOException, InterruptedException, NoSuchAlgorithmException, IllegalArgumentException;
}
