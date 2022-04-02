package com.sep.carsharingbusiness.graphQLServices;

import com.sep.carsharingbusiness.model.Vehicle;

import java.io.IOException;
import java.util.ArrayList;

public interface IVehicleService {
    Vehicle getVehicle(String licenseNo) throws IOException, InterruptedException;
    ArrayList<Vehicle> getVehiclesByOwnerCpr(String cpr) throws IOException, InterruptedException;
    ArrayList<Vehicle> getVehiclesByApprovalStatus(boolean isApproved) throws IOException, InterruptedException;
    Vehicle addVehicle(Vehicle vehicle) throws IOException, InterruptedException;
    Vehicle updateVehicle(Vehicle vehicle) throws IOException, InterruptedException;
    boolean removeVehicle(String licenseNo) throws IOException, InterruptedException;

}
