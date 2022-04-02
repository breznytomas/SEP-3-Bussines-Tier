package com.sep.carsharingbusiness.restControllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sep.carsharingbusiness.extentions.LocalDateTimeJsonAdapter;
import com.sep.carsharingbusiness.log.Log;
import com.sep.carsharingbusiness.logic.ILeaseLogic;
import com.sep.carsharingbusiness.model.Lease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@RestController
public class LeaseController {
    private final ILeaseLogic leaseLogic;

    private final Gson gson;

    @Autowired
    public LeaseController(ILeaseLogic leaseLogic) {
        this.leaseLogic = leaseLogic;
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonAdapter())
                .create();
    }


    @GetMapping(value = "/leases/{id}")
    public synchronized String getLeaseById(@PathVariable int id) {
        try {
            Log.addLog("|restControllers/LeaseController.getLeaseById| : Request : Id:" + id );
            return gson.toJson(leaseLogic.getLeaseById(id));

        } catch (IOException | InterruptedException | InternalError e) {
            Log.addLog("|restControllers/LeaseController.getLeaseById| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @GetMapping(value = "/leases/listing")
    public synchronized String getLeasesByListing(@RequestParam(value = "id") int id) {
        try {
            Log.addLog("|restControllers/LeaseController.getLeasesByListing| : Request : ListingId: " + id);
            return gson.toJson(leaseLogic.getLeasesByListing(id));

        } catch (IOException | InterruptedException e) {
            Log.addLog("|restControllers/LeaseController.getLeasesByListing| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @GetMapping(value = "/leases/customer")
    public synchronized String getLeasesByCustomer(@RequestParam(value = "cpr") String cpr) {
        try {
            Log.addLog("|restControllers/LeaseController.getLeasesByCustomer| : Request : Cpr: " + cpr);
            return gson.toJson(leaseLogic.getLeasesByCustomer(cpr));

        } catch (IOException | InterruptedException e) {
            Log.addLog("|restControllers/LeaseController.getLeasesByCustomer| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(value = "/leases")
    public synchronized String addLease(@RequestBody String json) {
        try {
            Log.addLog("|restControllers/LeaseController.addLease| : Request : " + json);

            Lease lease = gson.fromJson(json, Lease.class);
            return gson.toJson(leaseLogic.addLease(lease));

        } catch (IOException | InterruptedException | IllegalArgumentException e) {
            Log.addLog("|restControllers/LeaseController.addLease| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(value = "/leases/coupons/{code}")
    public synchronized String validateLease(@RequestBody String json, @PathVariable(value = "code", required = false) String code) {
        try {
            Log.addLog("|restControllers/LeaseController.validateLease| : Request : Coupon: " + code + " | " + json );

            Lease lease = gson.fromJson(json, Lease.class);
            return gson.toJson(leaseLogic.validateLease(lease, code));

        } catch (IOException | InterruptedException | IllegalArgumentException | InternalError e) {
            Log.addLog("|restControllers/LeaseController.validateLease| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/leases/{id}")
    public synchronized HttpStatus cancelLease(@PathVariable int id) {
        try {
            Log.addLog("|restControllers/LeaseController.cancelLease| : Request : " + id);
            leaseLogic.cancelLease(id);
            return HttpStatus.OK;

        } catch (IOException | InterruptedException e) {
            Log.addLog("|restControllers/LeaseController.cancelLease| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
