package com.sep.carsharingbusiness.graphQLServices;

import com.sep.carsharingbusiness.model.Listing;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IListingService {
    ArrayList<Listing> getListings(String location, LocalDateTime dateFrom, LocalDateTime dateTo) throws IOException, InterruptedException;
    Listing getListingById(int id) throws IOException, InterruptedException;
    ArrayList<Listing> getListingsByVehicle(String licenseNo) throws IOException, InterruptedException;
    Listing addListing(Listing listing) throws IOException, InterruptedException;
    Listing updateListing(Listing listing) throws IOException, InterruptedException;
    boolean removeListing(int id) throws IOException, InterruptedException;
}
