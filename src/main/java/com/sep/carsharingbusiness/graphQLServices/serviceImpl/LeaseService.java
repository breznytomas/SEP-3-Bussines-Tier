package com.sep.carsharingbusiness.graphQLServices.serviceImpl;

import com.sep.carsharingbusiness.graphQLServices.ILeaseService;
import com.sep.carsharingbusiness.model.Lease;
import com.sep.carsharingbusiness.mutations.MutationEnum;
import com.sep.carsharingbusiness.queries.QueryEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class LeaseService implements ILeaseService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(formatter) + "Z";
    }

    @SessionScope
    public ArrayList<Lease> getLeasesByListing(int listingId) throws IOException, InterruptedException {
        return GraphQLService.createListQuery(
                String.format(
                        GraphQLService.getQueryFromFile( QueryEnum.LeasesByListing.get(), false ),
                        listingId
                ),
                "leasesByListing",
                Lease.class
        );
    }

    @SessionScope
    public Lease getLeaseById(int id) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( QueryEnum.LeaseById.get(), false ),
                        id
                ),
                "lease",
                Lease.class
        );
    }

    @SessionScope
    public ArrayList<Lease> getLeasesByCustomer(String cpr) throws IOException, InterruptedException {
        return GraphQLService.createListQuery(
                String.format(
                        GraphQLService.getQueryFromFile( QueryEnum.LeasesByCustomer.get(), false ),
                        cpr
                ),
                "leasesByCustomer",
                Lease.class
        );
    }

    @SessionScope
    public Lease addLease(Lease lease) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( MutationEnum.AddLease.get(), true),
                        formatDateTime(lease.getLeasedFrom()),
                        formatDateTime(lease.getLeasedTo()),
                        lease.getTotalPrice(),
                        lease.listing.getId(),
                        lease.customer.getCpr()
                ),
                "addLease",
                Lease.class
        );
    }

    @SessionScope
    public Lease updateLease(Lease lease) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile( MutationEnum.UpdateLease.get(), true),
                        lease.getId(),
                        formatDateTime(lease.getLeasedFrom()),
                        formatDateTime(lease.getLeasedTo()),
                        lease.isCanceled(),
                        lease.getTotalPrice(),
                        lease.listing.getId(),
                        lease.customer.getCpr()
                ),
                "updateLease",
                Lease.class
        );
    }

    @SessionScope
    public boolean removeLease(int id) throws IOException, InterruptedException {
        return GraphQLService.createBooleanResponse(
                GraphQLService.sendQuery(
                        String.format(
                                GraphQLService.getQueryFromFile( MutationEnum.RemoveLease.get(), true),
                                id
                        )
                ),
                "removeLease"
        );
    }
}
