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
    private Integer orderid;
    
    @Column(name = "CustomerId")
    private Integer Customerid;

    @ManyToOne
    @JoinColumn(name = "CustomerId", referencedColumnName = "CustomerId", insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "OrderDate")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "Status")
    private Integer status;
}

