package com.sep.carsharingbusiness.graphQLServices;

import com.sep.carsharingbusiness.model.Lease;

import java.io.IOException;
import java.util.ArrayList;

public interface ILeaseService {
    ArrayList<Lease> getLeasesByListing(int listingId) throws IOException, InterruptedException;
    Lease getLeaseById(int id) throws IOException, InterruptedException;
    ArrayList<Lease> getLeasesByCustomer(String cpr) throws IOException, InterruptedException;
    Lease addLease(Lease lease) throws IOException, InterruptedException;
    Lease updateLease(Lease lease) throws IOException, InterruptedException;
    boolean removeLease(int id) throws IOException, InterruptedException;
}
