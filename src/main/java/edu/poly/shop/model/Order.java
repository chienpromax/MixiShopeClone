package edu.poly.shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customerid;

    @Column(name = "OrderDate")
    private Date orderDate;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "Status")
    private Integer status;
}

