package edu.poly.shop.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.model.Order;

public interface OrderService {

	// hiển thị hóa đơn
	List<Order> findByUsername(String username);
    Page<Order> findByUsername(String username, Pageable pageable);
    Page<Order> findByOrderDateAndUsername(Date orderDate, String username, Pageable pageable);

	// cập nhật giỏ hàng
	Order findPendingOrderByCustomerId(Integer customerId);

	//thêm giỏ hàng
	void addProductToCart(Integer customerid, Long productid);

	 // cập nhật status
	 void updateOrderStatus(Integer orderId, int status);

	Page<Order> findByOrderDate(Date orderDate, Pageable pageable);

	void deleteAll();

	<S extends Order> List<S> findAll(Example<S> example, Sort sort);

	void deleteAll(Iterable<? extends Order> entities);

	void deleteAllById(Iterable<? extends Integer> ids);

	void delete(Order entity);

	Order getById(Integer id);

	void deleteById(Integer id);

	long count();

	Order getOne(Integer id);

	<S extends Order> boolean exists(Example<S> example);

	<S extends Order> long count(Example<S> example);

	boolean existsById(Integer id);

	Optional<Order> findById(Integer id);

	<S extends Order> Page<S> findAll(Example<S> example, Pageable pageable);

	List<Order> findAllById(Iterable<Integer> ids);

	List<Order> findAll();

	<S extends Order> List<S> saveAllAndFlush(Iterable<S> entities);

	<S extends Order> S saveAndFlush(S entity);

	Page<Order> findAll(Pageable pageable);

	void flush();

	List<Order> findAll(Sort sort);

	<S extends Order> Optional<S> findOne(Example<S> example);

	<S extends Order> List<S> saveAll(Iterable<S> entities);

	<S extends Order> S save(S entity);
}
