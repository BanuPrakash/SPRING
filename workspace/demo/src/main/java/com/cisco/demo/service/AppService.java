package com.cisco.demo.service;

import com.cisco.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class AppService {
    // wiring by Type
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DataSource dataSource;

    public void register() {
        try (Connection con = dataSource.getConnection()) {
            System.out.println(con.getMetaData());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        userRepo.addUser();
    }
}
