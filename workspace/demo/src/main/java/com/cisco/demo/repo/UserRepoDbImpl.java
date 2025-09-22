package com.cisco.demo.repo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnMissingBean(name="userRepoMongoImpl")
public class UserRepoDbImpl implements UserRepo{
    @Override
    public void addUser() {
        System.out.println("User in database!!!");
    }
}
