package com.sep.carsharingbusiness.restControllers;

import com.google.gson.Gson;
import com.sep.carsharingbusiness.log.Log;
import com.sep.carsharingbusiness.logic.IUserLogic;
import com.sep.carsharingbusiness.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
public class UserController {
    private final IUserLogic userLogic;

    private final Gson gson;

    @Autowired
    public UserController(IUserLogic userLogic) {
        this.userLogic = userLogic;
        gson = new Gson();
    }


    @PostMapping(value = "/session")
    public synchronized String login(@RequestBody String json) {
        try {
            Log.addLog("|restControllers/AccountController.login| : Request : " + json);
            Account account = gson.fromJson(json, Account.class);
            return gson.toJson(userLogic.login(account));

        } catch (IOException | InterruptedException | NoSuchAlgorithmException e) {
            Log.addLog("|restControllers/AccountController.login| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        catch (IllegalAccessException | InternalError e) {
            Log.addLog("|restControllers/AccountController.login| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping(value = "/accounts")
    public synchronized String register(@RequestBody String json) {
        try {
            Log.addLog("|restControllers/AccountController.register| : Request : " + json);
            Account account = gson.fromJson(json, Account.class);
            return gson.toJson(userLogic.register(account));
        }
        catch (IllegalArgumentException e) {
            Log.addLog("|restControllers/AccountController.login| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        catch (IOException | InterruptedException |  NoSuchAlgorithmException | InternalError e) {
            Log.addLog("|restControllers/AccountController.register| : Error : " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
