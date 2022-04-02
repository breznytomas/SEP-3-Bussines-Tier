package com.sep.carsharingbusiness.logic;

import com.sep.carsharingbusiness.model.Lease;

import java.io.IOException;
import java.util.ArrayList;

public interface ILeaseLogic {
    Lease getLeaseById(int id) throws IOException, InterruptedException;
    ArrayList<Lease> getLeasesByListing(int listingId) throws IOException, InterruptedException;
    ArrayList<Lease> getLeasesByCustomer(String cpr) throws IOException, InterruptedException;
    Lease addLease(Lease lease) throws IOException, InterruptedException, IllegalArgumentException;
    Lease validateLease(Lease lease, String couponCode) throws IOException, InterruptedException, IllegalArgumentException, InternalError;
    boolean cancelLease(int id) throws IOException, InterruptedException;
}
