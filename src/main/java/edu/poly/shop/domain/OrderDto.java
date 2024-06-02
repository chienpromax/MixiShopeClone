package edu.poly.shop.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

	private int orderId;
	private Date orderDate;
	private int customerId;
	private double amount;
	private short status;
}
