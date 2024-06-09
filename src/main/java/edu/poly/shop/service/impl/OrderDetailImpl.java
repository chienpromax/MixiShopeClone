package edu.poly.shop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.poly.shop.domain.OrderDetailDto;
import edu.poly.shop.model.OrderDetail;
import edu.poly.shop.model.Product;
import edu.poly.shop.repository.OrderDetailRepository;
import edu.poly.shop.repository.ProductRepository;
import edu.poly.shop.service.OrderDetailService;
import jakarta.persistence.EntityManager;

@Service
public class OrderDetailImpl implements OrderDetailService {

	@Autowired
	private OrderDetailRepository orderDetailRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<OrderDetailDto> findAllOrderDetailsWithProductsByUsername(String username) {
		List<OrderDetail> orderDetails = orderDetailRepository.findByUsername(username);
		List<OrderDetailDto> orderDetailDtos = new ArrayList<>();

		for (OrderDetail orderDetail : orderDetails) {
			OrderDetailDto dto = new OrderDetailDto();
			BeanUtils.copyProperties(orderDetail, dto);

			Optional<Product> productOpt = productRepository.findById(orderDetail.getProductid());
			if (productOpt.isPresent()) {
				Product product = productOpt.get();
				dto.setProduct(product);
				dto.setProductName(product.getName());
				dto.setProductImage(product.getImage());
				dto.setProductPrice(product.getPrice());
			}

			orderDetailDtos.add(dto);
		}
		return orderDetailDtos;
	}

	@Override
	public void increaseQuantity(Long orderDetailId) {
		// Tìm orderDetail bằng ID
		OrderDetail orderDetail = orderDetailRepository.getOne(orderDetailId, entityManager);

		// Tăng số lượng sản phẩm lên 1
		int newQuantity = orderDetail.getQuantity() + 1;

		// Cập nhật số lượng mới
		orderDetail.setQuantity(newQuantity);

		// Lưu orderDetail đã thay đổi
		orderDetailRepository.save(orderDetail);
	}

	@Override
	public void decreaseOrRemove(Long orderDetailId) {
		// Tìm orderDetail bằng ID
		OrderDetail orderDetail = orderDetailRepository.getOne(orderDetailId, entityManager);

		// Tăng số lượng sản phẩm lên 1
		int newQuantity = orderDetail.getQuantity() - 1;

		// Cập nhật số lượng mới
		orderDetail.setQuantity(newQuantity);

		// Lưu orderDetail đã thay đổi
		orderDetailRepository.save(orderDetail);
	}

	@Override
	public Page<OrderDetail> findByOrderid(Integer orderid, Pageable pageable) {
		return orderDetailRepository.findByOrderid(orderid, pageable);
	}

	@Override
	public <S extends OrderDetail> S save(S entity) {
		return orderDetailRepository.save(entity);
	}

	@Override
	public <S extends OrderDetail> List<S> saveAll(Iterable<S> entities) {
		return orderDetailRepository.saveAll(entities);
	}

	@Override
	public <S extends OrderDetail> Optional<S> findOne(Example<S> example) {
		return orderDetailRepository.findOne(example);
	}

	@Override
	public List<OrderDetail> findAll(Sort sort) {
		return orderDetailRepository.findAll(sort);
	}

	@Override
	public void flush() {
		orderDetailRepository.flush();
	}

	@Override
	public Page<OrderDetail> findAll(Pageable pageable) {
		return orderDetailRepository.findAll(pageable);
	}

	@Override
	public <S extends OrderDetail> S saveAndFlush(S entity) {
		return orderDetailRepository.saveAndFlush(entity);
	}

	@Override
	public List<OrderDetail> findAll() {
		return orderDetailRepository.findAll();
	}

	@Override
	public List<OrderDetail> findAllById(Iterable<Integer> ids) {
		return orderDetailRepository.findAllById(ids);
	}

	@Override
	public <S extends OrderDetail> Page<S> findAll(Example<S> example, Pageable pageable) {
		return orderDetailRepository.findAll(example, pageable);
	}

	@Override
	public Optional<OrderDetail> findById(Integer id) {
		return orderDetailRepository.findById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return orderDetailRepository.existsById(id);
	}

	@Override
	public <S extends OrderDetail> boolean exists(Example<S> example) {
		return orderDetailRepository.exists(example);
	}

	@Override
	public OrderDetail getOne(Integer id) {
		return orderDetailRepository.getOne(id);
	}

	@Override
	public long count() {
		return orderDetailRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		orderDetailRepository.deleteById(id);
	}

	@Override
	public OrderDetail getById(Integer id) {
		return orderDetailRepository.getById(id);
	}

	@Override
	public void delete(OrderDetail entity) {
		orderDetailRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Integer> ids) {
		orderDetailRepository.deleteAllById(ids);
	}

	@Override
	public void deleteAll(Iterable<? extends OrderDetail> entities) {
		orderDetailRepository.deleteAll(entities);
	}

	@Override
	public <S extends OrderDetail> List<S> findAll(Example<S> example) {
		return orderDetailRepository.findAll(example);
	}

	@Override
	public <S extends OrderDetail> List<S> findAll(Example<S> example, Sort sort) {
		return orderDetailRepository.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		orderDetailRepository.deleteAll();
	}

}
