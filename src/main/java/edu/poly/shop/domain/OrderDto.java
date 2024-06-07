package edu.poly.shop.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Integer orderid;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private Integer customerid;
    private double amount;
    private Integer status;
    
    private Boolean isEdit = false;
}

