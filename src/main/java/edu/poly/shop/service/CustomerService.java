package edu.poly.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import edu.poly.shop.model.Customer;

public interface CustomerService {

	Customer findByUsername(String username);

	void deleteAll();

	<S extends Customer> List<S> findAll(Example<S> example, Sort sort);

	<S extends Customer> List<S> findAll(Example<S> example);

	void deleteAll(Iterable<? extends Customer> entities);

	Customer getReferenceById(Long id);

	void delete(Customer entity);

	Customer getById(Long id);

	void deleteById(Long id);

	long count();

	Customer getOne(Long id);

	<S extends Customer> boolean exists(Example<S> example);

	<S extends Customer> long count(Example<S> example);

	boolean existsById(Long id);

	Optional<Customer> findById(Long id);

	<S extends Customer> Page<S> findAll(Example<S> example, Pageable pageable);

	List<Customer> findAllById(Iterable<Long> ids);

	List<Customer> findAll();

	Page<Customer> findAll(Pageable pageable);

	void flush();

	List<Customer> findAll(Sort sort);

	<S extends Customer> Optional<S> findOne(Example<S> example);

	<S extends Customer> List<S> saveAll(Iterable<S> entities);

	<S extends Customer> S save(S entity);

	List<Customer> findByFullNameContaining(String fullName);

	Page<Customer> findByFullNameContaining(String fullName, Pageable pageable);

}
