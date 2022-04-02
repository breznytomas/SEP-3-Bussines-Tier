package com.sep.carsharingbusiness.graphQLServices.serviceImpl;

import com.sep.carsharingbusiness.graphQLServices.ICustomerService;
import com.sep.carsharingbusiness.model.Customer;
import com.sep.carsharingbusiness.mutations.MutationEnum;
import com.sep.carsharingbusiness.queries.QueryEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;

@Service
public class CustomerService implements ICustomerService {

    @SessionScope
    public Customer getCustomer(String cpr) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( QueryEnum.CustomerByCpr.get(), false),
                        cpr
                ),
                "customer",
                Customer.class
        );
    }

    @SessionScope
    public Customer addCustomer(Customer customer) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( MutationEnum.AddCustomer.get(), true),
                        customer.getCpr(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getPhoneNo()
                ),
                "addCustomer",
                Customer.class
        );
    }

    @SessionScope
    public Customer updateCustomer(Customer customer) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( MutationEnum.UpdateCustomer.get(), true),
                        customer.getCpr(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getPhoneNo()
                ),
                "updateCustomer",
                Customer.class
        );
    }

    @SessionScope
    public boolean removeCustomer(String cpr) throws IOException, InterruptedException {
        return GraphQLService.createBooleanResponse(
                GraphQLService.sendQuery(
                        String.format(
                                GraphQLService.getQueryFromFile( MutationEnum.RemoveCustomer.get(), true),
                                cpr
                        )
                ),
                "removeCustomer"
        );
    }
}
