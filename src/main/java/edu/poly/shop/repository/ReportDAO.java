package edu.poly.shop.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.poly.shop.domain.Report;

public interface ReportDAO extends JpaRepository<Report, String> {

    @Query("SELECT new edu.poly.shop.domain.Report(c.name AS categoryName, SUM(od.quantity) AS totalQuantitySold, SUM(od.quantity * od.unitPrice) AS totalRevenue) "
            + "FROM OrderDetail od JOIN od.product p JOIN p.category c "
            + "GROUP BY c.name ORDER BY SUM(od.quantity * od.unitPrice) DESC")
    List<Report> getRevenueCategory();
}
