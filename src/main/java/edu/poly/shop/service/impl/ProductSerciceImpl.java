package edu.poly.shop.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import edu.poly.shop.model.Product;
import edu.poly.shop.repository.ProductRepository;
import edu.poly.shop.service.ProductService;

@Service
public class ProductSerciceImpl implements ProductService {

	ProductRepository productRepository;

	public ProductSerciceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
    public Long countAllProducts() {
        return productRepository.count();
    }

	@Override
	public Page<Product> findByCategoryidAndNameContaining(Long categoryId, String name, Pageable pageable) {
		return productRepository.findByCategoryidAndNameContaining(categoryId, name, pageable);
	}

	// Lọc sản phẩm theo categoryid và giá trong khoảng minPrice và maxPrice
	@Override
	public Page<Product> findByCategoryidAndPriceBetween(Long categoryId, Double minPrice, Double maxPrice,
			Pageable pageable) {
		return productRepository.findByCategoryidAndPriceBetween(categoryId, minPrice, maxPrice, pageable);
	}

	// Lọc sản phẩm theo giá trong khoảng minPrice và maxPrice
	@Override
	public Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable) {
		return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
	}

	// phân trang doanh muc
	@Override
	public Page<Product> findByCategoryid(Long categoryid, Pageable pageable) {
		return productRepository.findByCategoryid(categoryid, pageable);
	}

	@Override
	public List<Product> findByNameContaining(String name) {
		return productRepository.findByNameContaining(name);
	}

	@Override
	public Page<Product> findByNameContaining(String name, Pageable pageable) {
		return productRepository.findByNameContaining(name, pageable);
	}

	@Override
	public <S extends Product> S save(S entity) {
		return productRepository.save(entity);
	}

	@Override
	public <S extends Product> List<S> saveAll(Iterable<S> entities) {
		return productRepository.saveAll(entities);
	}

	@Override
	public <S extends Product> Optional<S> findOne(Example<S> example) {
		return productRepository.findOne(example);
	}

	@Override
	public List<Product> findAll(Sort sort) {
		return productRepository.findAll(sort);
	}

	@Override
	public void flush() {
		productRepository.flush();
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public <S extends Product> S saveAndFlush(S entity) {
		return productRepository.saveAndFlush(entity);
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> findAllById(Iterable<Long> ids) {
		return productRepository.findAllById(ids);
	}

	@Override
	public <S extends Product> Page<S> findAll(Example<S> example, Pageable pageable) {
		return productRepository.findAll(example, pageable);
	}

	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return productRepository.existsById(id);
	}

	@Override
	public <S extends Product> long count(Example<S> example) {
		return productRepository.count(example);
	}

	@Override
	public <S extends Product> boolean exists(Example<S> example) {
		return productRepository.exists(example);
	}

	@Override
	public Product getOne(Long id) {
		return productRepository.getOne(id);
	}

	@Override
	public long count() {
		return productRepository.count();
	}

	@Override
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}

	@Override
	public Product getById(Long id) {
		return productRepository.getById(id);
	}

	@Override
	public void delete(Product entity) {
		productRepository.delete(entity);
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		productRepository.deleteAllById(ids);
	}

	@Override
	public Product getReferenceById(Long id) {
		return productRepository.getReferenceById(id);
	}

	@Override
	public void deleteAll(Iterable<? extends Product> entities) {
		productRepository.deleteAll(entities);
	}

	@Override
	public <S extends Product> List<S> findAll(Example<S> example) {
		return productRepository.findAll(example);
	}

	@Override
	public <S extends Product> List<S> findAll(Example<S> example, Sort sort) {
		return productRepository.findAll(example, sort);
	}

	@Override
	public void deleteAll() {
		productRepository.deleteAll();
	}

}
