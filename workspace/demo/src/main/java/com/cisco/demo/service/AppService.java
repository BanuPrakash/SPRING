package com.cisco.demo.service;

import com.cisco.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AppService {
    // wiring by Type
    @Autowired
    private UserRepo userRepo;

    public void register() {
        userRepo.addUser();
    }
}
