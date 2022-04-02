package com.sep.carsharingbusiness.logic.logicImpl;

import com.sep.carsharingbusiness.graphQLServices.IListingService;
import com.sep.carsharingbusiness.graphQLServices.IVehicleService;
import com.sep.carsharingbusiness.logic.IVehicleLogic;
import com.sep.carsharingbusiness.model.Listing;
import com.sep.carsharingbusiness.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class VehicleLogic implements IVehicleLogic {

    private final IVehicleService vehicleService;
    private final IListingService listingService;

    @Autowired
    public VehicleLogic(IVehicleService vehicleService, IListingService listingService) {
        this.vehicleService = vehicleService;
        this.listingService = listingService;
    }

    @SessionScope
    public Vehicle getVehicle(String licenseNo) throws IOException, InterruptedException {
        return vehicleService.getVehicle(licenseNo);
    }

    @SessionScope
    public ArrayList<Vehicle> getVehiclesByOwnerCpr(String cpr) throws IOException, InterruptedException {
        return vehicleService.getVehiclesByOwnerCpr(cpr);
    }

    @SessionScope
    public ArrayList<Vehicle> getVehiclesWaitingForApproval() throws IOException, InterruptedException {
        return vehicleService.getVehiclesByApprovalStatus(false);
    }

    @SessionScope
    public Vehicle addVehicle(Vehicle vehicle) throws IOException, InterruptedException, IllegalArgumentException {
        try {
            vehicleService.getVehicle(vehicle.getLicenseNo());
        }
        catch (InternalError e){
            // Did not find the vehicle
            return vehicleService.addVehicle(vehicle);
        }
        throw new IllegalArgumentException("The vehicle with licenseNo '" + vehicle.getLicenseNo() + "' is already added.");
    }

    @SessionScope
    public Vehicle updateVehicle(Vehicle vehicle) throws IOException, InterruptedException {
        return vehicleService.updateVehicle(vehicle);
    }

    @SessionScope
    public boolean removeVehicle(String licenseNo) throws IOException, InterruptedException {
        ArrayList<Listing> listings = listingService.getListingsByVehicle(licenseNo);
        for (Listing listing : listings) {
            listingService.removeListing(listing.getId());
        }
        return vehicleService.removeVehicle(licenseNo);
    }
}
