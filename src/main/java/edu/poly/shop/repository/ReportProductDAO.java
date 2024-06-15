package edu.poly.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.poly.shop.domain.ReportProduct;

public interface ReportProductDAO extends JpaRepository<ReportProduct, String> {

     // Truy vấn thống kê sản phẩm bán chạy nhất
    @Query("SELECT new edu.poly.shop.domain.ReportProduct(p.name AS productName, SUM(od.quantity) AS totalQuantitySold, SUM(od.quantity * od.unitPrice) AS totalRevenue) "
           + "FROM OrderDetail od JOIN od.product p "
           + "GROUP BY p.name ORDER BY SUM(od.quantity) DESC")
    List<ReportProduct> getBestSellingProducts();

}
