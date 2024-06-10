package edu.poly.shop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.poly.shop.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    Page<OrderDetail> findByOrderid(Integer orderid, Pageable pageable);

    // List<OrderDetail> findByOrderid(Integer orderid);

    @Query("SELECT od FROM OrderDetail od JOIN od.order o JOIN o.customer c WHERE c.username = :username")
    List<OrderDetail> findByUsername(@Param("username") String username);

    // Tìm chi tiết đơn hàng dựa trên id đơn hàng và id sản phẩm
    Optional<OrderDetail> findOneByOrderidAndProductid(Integer orderId, Long productId);
}

