package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.poly.shop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContaining(String name);
    
    Page<Product> findByNameContaining(String name, Pageable pageable);
    
    // List<Product> findByCategoryid(Long categoryid);

    Page<Product> findByCategoryid(Long categoryId, Pageable pageable);

    // Tìm kiếm sản phẩm theo categoryid và giá trong khoảng minPrice và maxPrice
    Page<Product> findByCategoryidAndPriceBetween(Long categoryId, Double minPrice, Double maxPrice, Pageable pageable);

    // Tìm kiếm sản phẩm theo giá trong khoảng minPrice và maxPrice
    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    Page<Product> findByCategoryidAndNameContaining(Long categoryId, String name, Pageable pageable);

}
