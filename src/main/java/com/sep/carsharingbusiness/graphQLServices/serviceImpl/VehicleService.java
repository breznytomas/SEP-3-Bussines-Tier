package com.sep.carsharingbusiness.graphQLServices.serviceImpl;


import com.sep.carsharingbusiness.graphQLServices.IVehicleService;
import com.sep.carsharingbusiness.model.Vehicle;
import com.sep.carsharingbusiness.mutations.MutationEnum;
import com.sep.carsharingbusiness.queries.QueryEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class VehicleService implements IVehicleService {

    @SessionScope
    public Vehicle getVehicle(String licenseNo) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile(QueryEnum.VehicleByLicenseNo.get(), false),
                        licenseNo
                ),
                "vehicle",
                Vehicle.class
        );
    }

    @SessionScope
    public ArrayList<Vehicle> getVehiclesByOwnerCpr(String cpr) throws IOException, InterruptedException {
        return GraphQLService.createListQuery(
                String.format(
                        GraphQLService.getQueryFromFile(QueryEnum.VehiclesByOwnerCpr.get(), false),
                        cpr
                ),
                "vehiclesByOwner",
                Vehicle.class
        );
    }

    @Override
    public ArrayList<Vehicle> getVehiclesByApprovalStatus(boolean isApproved) throws IOException, InterruptedException {
        return GraphQLService.createListQuery(
                String.format(
                        GraphQLService.getQueryFromFile(QueryEnum.VehiclesByApprovalStatus.get(), false),
                        isApproved
                ),
                "vehiclesByApprovalStatus",
                Vehicle.class
        );
    }

    @SessionScope
    public Vehicle addVehicle(Vehicle vehicle) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile(MutationEnum.AddVehicle.get(), true),
                        vehicle.getLicenseNo(),
                        vehicle.getBrand(),
                        vehicle.getModel(),
                        vehicle.getTransmission(),
                        vehicle.getFuelType(),
                        vehicle.getType(),
                        vehicle.getSeats(),
                        vehicle.getMileage(),
                        vehicle.getManufactureYear(),
                        vehicle.owner.getCpr()
                ),
                "addVehicle",
                Vehicle.class
        );
    }

    @SessionScope
    public Vehicle updateVehicle(Vehicle vehicle) throws IOException, InterruptedException {
        return GraphQLService.createObjQuery(
                String.format(
                        GraphQLService.getQueryFromFile(MutationEnum.UpdateVehicle.get(), true),
                        vehicle.getLicenseNo(),
                        vehicle.getBrand(),
                        vehicle.getModel(),
                        vehicle.getTransmission(),
                        vehicle.getFuelType(),
                        vehicle.getType(),
                        vehicle.getSeats(),
                        vehicle.getMileage(),
                        vehicle.getManufactureYear(),
                        vehicle.owner.getCpr(),
                        vehicle.isApproved()
                ),
                "updateVehicle",
                Vehicle.class
        );
    }

    @SessionScope
    public boolean removeVehicle(String licenseNo) throws IOException, InterruptedException {
        return GraphQLService.createBooleanResponse(
                GraphQLService.sendQuery(
                        String.format(
                                GraphQLService.getQueryFromFile(MutationEnum.RemoveVehicle.get(), true),
                                licenseNo
                        )
                ),
                "removeVehicle"
        );
    }
}
