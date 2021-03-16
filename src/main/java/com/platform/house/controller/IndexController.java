package com.platform.house.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.platform.house.domain.User;
import com.platform.house.repo.UserRepo;

@RestController
public class IndexController {

    private Gson gson = new Gson();

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/test")
    public String test() {
        String hostname;
        try {
            hostname = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            hostname = "unknown";
        }

        return "Test Spring Boot @ " + hostname;
    }

    @GetMapping(value = "/users")
    public String listUsers() { 
        List<User> users = userRepo.findNotAdminUsers();
        return gson.toJson(users);
    }

}
