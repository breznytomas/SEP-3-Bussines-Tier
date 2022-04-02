package com.sep.carsharingbusiness.logic.logicImpl;

import com.sep.carsharingbusiness.graphQLServices.IAccountService;
import com.sep.carsharingbusiness.graphQLServices.ICustomerService;
import com.sep.carsharingbusiness.logic.IUserLogic;
import com.sep.carsharingbusiness.model.Account;
import com.sep.carsharingbusiness.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserLogic implements IUserLogic {
    private final IAccountService accountService;
    private final ICustomerService customerService;

    @Autowired
    public UserLogic(IAccountService accountService, ICustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    @SessionScope
    public Customer login(Account account) throws IOException, InterruptedException, IllegalAccessException, NoSuchAlgorithmException {
        Account toCompare = accountService.getAccount(account.getUsername());
        account.setPassword(
                hash(account.getPassword())
        );

        if (account.getPassword().equals(toCompare.getPassword())) {
            return toCompare.customer;
        }
        throw new IllegalAccessException("Incorrect password.");
    }

    @SessionScope
    public Customer register(Account account) throws IOException, InterruptedException, NoSuchAlgorithmException, IllegalArgumentException {
        try {
            accountService.getAccount(account.getUsername());
            throw new IllegalArgumentException("Username is already used. Please try another one.");
        }
        // Did not find the any users with that username.
        catch (InternalError ignored) {
            account.setPassword(
                    hash(account.getPassword())
            );
            customerService.addCustomer(account.customer);
            Account registered = accountService.addAccount(account);
            return customerService.getCustomer(registered.customer.getCpr());
        }

    }

    private String hash(String password) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] passBytes = password.getBytes(StandardCharsets.UTF_8);
        byte[] passHash = sha256.digest(passBytes);
        return  Base64.getEncoder().encodeToString(passHash);
    }
}
