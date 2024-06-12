package edu.poly.shop.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportMonth {
    
    @Id
    private Integer month;
    private Long totalQuantitySold;
    private Double totalRevenue;
}
