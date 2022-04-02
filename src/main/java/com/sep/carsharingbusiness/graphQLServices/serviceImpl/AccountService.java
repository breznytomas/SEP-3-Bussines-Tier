package com.sep.carsharingbusiness.graphQLServices.serviceImpl;

import com.sep.carsharingbusiness.graphQLServices.IAccountService;
import com.sep.carsharingbusiness.model.Account;
import com.sep.carsharingbusiness.mutations.MutationEnum;
import com.sep.carsharingbusiness.queries.QueryEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;

@Service
public class AccountService implements IAccountService {

    @SessionScope
    public Account getAccount(String username) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( QueryEnum.AccountByUsername.get(), false),
                        username
                ),
                "account",
                Account.class
        );
    }

    @SessionScope
    public Account addAccount(Account account) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( MutationEnum.AddAccount.get(), true),
                        account.getUsername(),
                        account.getPassword(),
                        account.customer.getCpr()
                ),
                "addAccount",
                Account.class
        );
    }
}
