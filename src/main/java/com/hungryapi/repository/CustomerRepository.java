package com.hungryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hungryapi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer , Long> {
}
