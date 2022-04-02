package com.sep.carsharingbusiness.graphQLServices;

import com.sep.carsharingbusiness.model.Account;

import java.io.IOException;

public interface IAccountService {
    Account getAccount(String username) throws IOException, InterruptedException;
    Account addAccount(Account account) throws IOException, InterruptedException;
}
