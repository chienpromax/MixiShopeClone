package edu.poly.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailId")
    private Integer orderDetailid;

    @Column(name = "OrderId")
    private Integer orderid;

    @ManyToOne
    @JoinColumn(name = "OrderId", referencedColumnName = "OrderId", insertable = false, updatable = false)
    private Order order; // Changed from "Order Order" to "Order order"

    @Column(name = "ProductId")
    private Long  productid;

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "ProductId", insertable = false, updatable = false)
    private Product product;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "UnitPrice")
    private Double unitPrice;
}