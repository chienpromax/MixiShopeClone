package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.poly.shop.model.OrderDetail;
import jakarta.persistence.EntityManager;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    default OrderDetail getOne(Long orderDetailId, EntityManager entityManager) {
        return entityManager.getReference(OrderDetail.class, orderDetailId);
    }

    Page<OrderDetail> findByOrderid(Integer orderid, Pageable pageable);

    @Query("SELECT od FROM OrderDetail od JOIN od.order o JOIN o.customer c WHERE c.username = :username")
    List<OrderDetail> findByUsername(@Param("username") String username);
}

