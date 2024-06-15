package edu.poly.shop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.poly.shop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Page<Order> findByOrderDate(Date orderDate, Pageable pageable);

    List<Order> findByCustomerUsername(String username);

    // thêm sản phẩm vào giỏ
    @Query("SELECT o FROM Order o WHERE o.Customerid = :customerId AND o.status = 0")
    Order findPendingOrderByCustomerId(@Param("customerId") Integer customerId);

    // hiển thị hóa đơn
    Page<Order> findByCustomerUsername(String username, Pageable pageable);

    Page<Order> findByOrderDateAndCustomerUsername(Date orderDate, String username, Pageable pageable);

}
