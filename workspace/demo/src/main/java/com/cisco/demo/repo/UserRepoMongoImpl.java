package com.cisco.demo.repo;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoMongoImpl implements UserRepo{
    @Override
    public void addUser() {
        System.out.println("Add User in MongoDB!!!");
    }
}
