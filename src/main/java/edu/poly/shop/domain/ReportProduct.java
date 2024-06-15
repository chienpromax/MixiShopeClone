package edu.poly.shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportProduct {
    
    @Id
    private String productName;
    private Long totalQuantitySold;
    private Double totalRevenue;
}
