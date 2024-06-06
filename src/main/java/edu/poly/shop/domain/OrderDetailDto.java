package edu.poly.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto{

	private int orderDetailid;
	private int orderid;
	private int productid;
	private int quantity;
	private double unitPrice;
}
