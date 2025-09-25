package com.cisco.vehiclerental.security.repo;


import com.cisco.vehiclerental.security.entity.User;
import com.cisco.vehiclerental.security.service.UserDetailsServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
