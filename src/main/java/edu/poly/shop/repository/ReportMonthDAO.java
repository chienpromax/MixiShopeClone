package edu.poly.shop.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.poly.shop.domain.ReportMonth;

public interface ReportMonthDAO extends JpaRepository<ReportMonth, Integer> {

    @Query("SELECT new edu.poly.shop.domain.ReportMonth(MONTH(o.orderDate) AS month, SUM(od.quantity) AS totalQuantitySold, SUM(od.quantity * od.unitPrice) AS totalRevenue) "
            + "FROM OrderDetail od JOIN od.order o "
            + "GROUP BY MONTH(o.orderDate) ORDER BY MONTH(o.orderDate)")
    List<ReportMonth> getRevenueByMonth();

}
