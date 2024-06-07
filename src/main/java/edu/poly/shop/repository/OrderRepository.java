package edu.poly.shop.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findByOrderDate(Date orderDate, Pageable pageable);
}
