package com.sep.carsharingbusiness.logic;

import com.sep.carsharingbusiness.model.Vehicle;

import java.io.IOException;
import java.util.ArrayList;

public interface IVehicleLogic {
    Vehicle getVehicle(String licenseNo) throws IOException, InterruptedException;
    ArrayList<Vehicle> getVehiclesByOwnerCpr(String cpr) throws IOException, InterruptedException;
    ArrayList<Vehicle> getVehiclesWaitingForApproval() throws IOException, InterruptedException;
    Vehicle addVehicle(Vehicle vehicle) throws IOException, InterruptedException, IllegalArgumentException;
    Vehicle updateVehicle(Vehicle vehicle) throws IOException, InterruptedException;
    boolean removeVehicle(String licenseNo) throws IOException, InterruptedException;
}
