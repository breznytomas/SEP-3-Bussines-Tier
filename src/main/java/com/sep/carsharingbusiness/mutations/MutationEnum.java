package com.sep.carsharingbusiness.mutations;

public enum MutationEnum {
    AddVehicle("AddVehicle.graphql"),
    UpdateVehicle("UpdateVehicle.graphql"),
    RemoveVehicle("RemoveVehicle.graphql"),

    AddListing("AddListing.graphql"),
    UpdateListing("UpdateListing.graphql"),
    RemoveListing("RemoveListing.graphql"),

    AddCustomer("AddCustomer.graphql"),
    UpdateCustomer("UpdateCustomer.graphql"),
    RemoveCustomer("RemoveCustomer.graphql"),

    AddLease("AddLease.graphql"),
    UpdateLease("UpdateLease.graphql"),
    RemoveLease("RemoveLease.graphql"),

    AddAccount("AddAccount.graphql"),

    AddCoupon("AddCoupon.graphql");

    private final String fileName;

    MutationEnum(String fileName) {
        this.fileName = fileName;
    }

    public String get() {
        return fileName;
    }
}
