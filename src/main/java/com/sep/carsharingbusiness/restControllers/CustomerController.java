package com.sep.carsharingbusiness.restControllers;

import com.google.gson.Gson;
import com.sep.carsharingbusiness.graphQLServices.ICustomerService;
import com.sep.carsharingbusiness.log.Log;
import com.sep.carsharingbusiness.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
public class CustomerController {
    private final ICustomerService customerService;

    private final Gson gson;

    @Autowired
    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
        gson = new Gson();
    }


    @GetMapping(value = "/customers")
    public synchronized String getCustomer(@RequestParam(value = "cpr") String cpr) {
        try {
            Log.addLog("|restControllers/CustomerController.getCustomer| : Request : Cpr:" + cpr);
            Customer customer = customerService.getCustomer(cpr);
            return gson.toJson(customer);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Log.addLog("|restControllers/CustomerController.getCustomer| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PostMapping(value = "/customers")
    public synchronized String addCustomer(@RequestBody String json) {
        try {
            Log.addLog("|restControllers/CustomerController.addVehicle| : Request : " + json);
            Customer customer = gson.fromJson(json, Customer.class);
            return gson.toJson(customerService.addCustomer(customer));

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Log.addLog("|restControllers/CustomerController.addVehicle| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @PatchMapping("/customers/{cpr}")
    public synchronized String updateCustomer(@RequestBody Customer customer, @PathVariable String cpr) {
        if (!customer.getCpr().equals(cpr)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The cpr from param does not match with the customer's cpr.");
        }
        try {
            String result = gson.toJson(customerService.updateCustomer(customer));
            Log.addLog("|restControllers/CustomerController.updateCustomer| : Reply :  " + result);
            return result;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Log.addLog("|restControllers/CustomerController.updateCustomer| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/customers/{cpr}")
    public synchronized HttpStatus removeCustomer(@PathVariable String cpr) {
        try {
            Log.addLog("|restControllers/CustomerController.removeCustomer| : Request : " + cpr);
            customerService.removeCustomer(cpr);
            return HttpStatus.OK;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Log.addLog("|restControllers/CustomerController.removeCustomer| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }
}
