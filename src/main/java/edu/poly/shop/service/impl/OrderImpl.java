package edu.poly.shop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.poly.shop.model.Order;
import edu.poly.shop.model.OrderDetail;
import edu.poly.shop.model.Product;
import edu.poly.shop.repository.OrderDetailRepository;
import edu.poly.shop.repository.OrderRepository;
import edu.poly.shop.repository.ProductRepository;
import edu.poly.shop.service.OrderService;

@Service
public class OrderImpl implements OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;

	public OrderImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	// thêm sản phẩm vào giở
	@Override
    public void addProductToCart(Integer customerId, Long productId) {
        Order order = orderRepository.findPendingOrderByCustomerId(customerId);

        if (order == null) {
            order = new Order();
            order.setCustomerid(customerId);
            order.setOrderDate(new Date());
            order.setAmount(0.0);
            order.setStatus(0); // Giả định 0 là trạng thái chưa thanh toán
            orderRepository.save(order);
        }

        // Kiểm tra xem sản phẩm đã tồn tại trong đơn hàng chưa
        Optional<OrderDetail> existingOrderDetailOpt = orderDetailRepository.findOneByOrderidAndProductid(order.getOrderid(), productId);
        if (existingOrderDetailOpt.isPresent()) {
            // Nếu sản phẩm đã tồn tại trong đơn hàng, chỉ cần cập nhật số lượng
            OrderDetail existingOrderDetail = existingOrderDetailOpt.get();
            existingOrderDetail.setQuantity(existingOrderDetail.getQuantity() + 1);
            orderDetailRepository.save(existingOrderDetail);

            // Cập nhật tổng tiền đơn hàng
            double totalPrice = existingOrderDetail.getUnitPrice() * existingOrderDetail.getQuantity();
            order.setAmount(order.getAmount() + totalPrice);
            orderRepository.save(order);
        } else {
            // Nếu sản phẩm chưa tồn tại trong đơn hàng, thêm mới
            Optional<Product> productOpt = productRepository.findById(productId);
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderid(order.getOrderid());
                orderDetail.setProductid(productId);
                orderDetail.setQuantity(1); // Giả định mỗi lần thêm 1 sản phẩm
                orderDetail.setUnitPrice(product.getPrice());
                orderDetailRepository.save(orderDetail);

                // Cập nhật tổng tiền đơn hàng
                order.setAmount(order.getAmount() + product.getPrice());
                orderRepository.save(order);
            }
        }
    }
	
	@Override
	public Page<Order> findByOrderDate(Date orderDate, Pageable pageable) {
		return orderRepository.findByOrderDate(orderDate, pageable);
	}

	@Override
	public <S extends Order> S save(S entity) {
		return orderRepository.save(entity);
	}

	@Override
	public <S extends Order> List<S> saveAll(Iterable<S> entities) {
		return orderRepository.saveAll(entities);
	}

	@Override
	public <S extends Order> Optional<S> findOne(Example<S> example) {
		return orderRepository.findOne(example);
	}

	@Override
	public List<Order> findAll(Sort sort) {
		return orderRepository.findAll(sort);
	}

	@Override
	public void flush() {
		orderRepository.flush();
	}

	@Override
	public Page<Order> findAll(Pageable pageable) {
		return orderRepository.findAll(pageable);
	}

	@Override
	public <S extends Order> S saveAndFlush(S entity) {
		return orderRepository.saveAndFlush(entity);
	}

	@Override
	public <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities) {
		return orderRepository.saveAllAndFlush(entities);
	}

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public List<Order> findAllById(Iterable<Integer> ids) {
		return orderRepository.findAllById(ids);
	}

	@Override
	public <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable) {
		return orderRepository.findAll(example, pageable);
	}

	@Override
	public Optional<Order> findById(Integer id) {
		return orderRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return orderRepository.existsById(id);
	}

	@Override
	public <S extends Order> long count(Example<S> example) {
		return orderRepository.count(example);
	}

	@Override
	public <S extends Order> boolean exists(Example<S> example) {
		return orderRepository.exists(example);
	}

	@Override
	public Order getOne(Integer id) {
		return orderRepository.getOne(id);
	}

	@Override
	public long count() {
		return orderRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		orderRepository.deleteById(id);
	}

	@Override
	public Order getById(Integer id) {
		return orderRepository.getById(id);
	}

	@Override
	public void delete(Order entity) {
		orderRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		orderRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAll(Iterable<? extends Order> entities) {
		orderRepository.deleteAll(entities);
	}

	@Override
	public <S extends Order> List<S> findAll(Example<S> example, Sort sort) {
		return orderRepository.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		orderRepository.deleteAll();
	}

}
