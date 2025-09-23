package com.cisco.vehiclerental.repo;

import com.cisco.vehiclerental.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, String> {
}
