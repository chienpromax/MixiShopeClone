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
    private Long orderDetailid;

    @ManyToOne
    @JoinColumn(name = "OrderId", nullable = false)
    private Order orderid;

    @ManyToOne
    @JoinColumn(name = "ProductId", nullable = false)
    private Product productid;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "UnitPrice")
    private Double unitPrice;
}