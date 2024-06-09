package edu.poly.shop.domain;

import edu.poly.shop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Product product;

    private int orderDetailid;
    private int orderid;
    private int productid;
    private int quantity;
    private double unitPrice;

    private Boolean isEdit = false;

    private String productName;
    private String productImage;
    private Double productPrice;
}

