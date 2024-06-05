package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.model.Product;
import edu.poly.shop.repository.ProductRepository;

public interface ProductService {
	
	Long countAllProducts();

	Page<Product> findByCategoryidAndNameContaining(Long categoryId, String name, Pageable pageable);
	
	// tìm theo khoảng giá
	Page<Product> findByCategoryidAndPriceBetween(Long categoryId, Double minPrice, Double maxPrice, Pageable pageable);

    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

	//phân trang doanh muc
	Page<Product> findByCategoryid(Long categoryid, Pageable pageable);
	
	void deleteAll();

	<S extends Product> List<S> findAll(Example<S> example, Sort sort);

	<S extends Product> List<S> findAll(Example<S> example);

	void deleteAll(Iterable<? extends Product> entities);

	Product getReferenceById(Long id);

	void deleteAllById(Iterable<? extends Long> ids);

	void delete(Product entity);

	Product getById(Long id);

	void deleteById(Long id);

	long count();

	Product getOne(Long id);

	<S extends Product> boolean exists(Example<S> example);

	<S extends Product> long count(Example<S> example);

	boolean existsById(Long id);

	Optional<Product> findById(Long id);

	<S extends Product> Page<S> findAll(Example<S> example, Pageable pageable);

	List<Product> findAllById(Iterable<Long> ids);

	List<Product> findAll();

	<S extends Product> S saveAndFlush(S entity);

	Page<Product> findAll(Pageable pageable);

	void flush();

	List<Product> findAll(Sort sort);

	<S extends Product> Optional<S> findOne(Example<S> example);

	<S extends Product> List<S> saveAll(Iterable<S> entities);

	<S extends Product> S save(S entity);

	Page<Product> findByNameContaining(String name, Pageable pageable);

	List<Product> findByNameContaining(String name);

}
