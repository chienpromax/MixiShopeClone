package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Customer findByUsername(String username);
    
    List<Customer> findByFullNameContaining(String fullName);
    
    Page<Customer> findByFullNameContaining(String fullName, Pageable pageable);

}
