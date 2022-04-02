package com.sep.carsharingbusiness.logic.logicImpl;

import com.sep.carsharingbusiness.graphQLServices.ILeaseService;
import com.sep.carsharingbusiness.graphQLServices.IListingService;
import com.sep.carsharingbusiness.logic.IListingLogic;
import com.sep.carsharingbusiness.model.Lease;
import com.sep.carsharingbusiness.model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ListingLogic implements IListingLogic {

    private final IListingService listingService;
    private final ILeaseService leaseService;

    @Autowired
    public ListingLogic(IListingService listingService, ILeaseService leaseService) {
        this.listingService = listingService;
        this.leaseService = leaseService;
    }

    private boolean isListingAvailable(LocalDateTime dateFrom, LocalDateTime dateTo, ArrayList<Lease> leasesForThisListing) {
        for (Lease lease : leasesForThisListing) {
            if (lease.isCanceled()) continue;
            if (
                    !( dateTo.isBefore(lease.getLeasedFrom())
                                    || dateFrom.isAfter(lease.getLeasedTo()) )
            ) {
                // if the intervals are overlapping
                return false;
            }
        }
        return true;
    }

    @SessionScope
    public ArrayList<Listing> getListings(String location, LocalDateTime dateFrom, LocalDateTime dateTo) throws IOException, InterruptedException {
        ArrayList<Listing> listings = listingService.getListings(location, dateFrom, dateTo);

        listings.removeIf( (listing) -> {
            ArrayList<Lease> leasesForAListing = null;
            try {
               leasesForAListing  = leaseService.getLeasesByListing(listing.getId());
            }
            catch (IOException | InterruptedException ignored) {}

            if (leasesForAListing == null) return false;
            return !isListingAvailable(dateFrom, dateTo, leasesForAListing);
        });

        return listings;
    }

    @SessionScope
    public Listing getListingById(int id) throws IOException, InterruptedException {
        return listingService.getListingById(id);
    }

    @Override
    public ArrayList<Listing> getListingsByVehicle(String licenseNo) throws IOException, InterruptedException {
        return listingService.getListingsByVehicle(licenseNo);
    }

    @SessionScope
    public Listing addListing(Listing listing) throws IOException, InterruptedException, IllegalArgumentException {
        if (listing.getDateTo().isBefore(listing.getDateFrom())) {
            throw new IllegalArgumentException("Listing's 'date to' cannot be before its 'date from'");

        }
        ArrayList<Listing> listings = listingService.getListingsByVehicle(listing.vehicle.getLicenseNo());
        if (!listings.isEmpty()) {
            throw new IllegalArgumentException("A listing is already created for the vehicle with licenseNo " + listing.vehicle.getLicenseNo());
        }
        return listingService.addListing(listing);
    }

    @SessionScope
    public Listing updateListing(Listing listing) throws IOException, InterruptedException {
        return listingService.updateListing(listing);
    }

    @SessionScope
    public boolean removeListing(int id) throws IOException, InterruptedException {
        return listingService.removeListing(id);
    }
}
