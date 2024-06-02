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
@Table(name = "Products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId")
    private Long productid;

    @Column(name = "Name", columnDefinition = "nvarchar(50) not null")
    private String name;

    private Integer quantity;

    private Double price;

    private Double discount;

    @Column(name = "Image", columnDefinition = "nvarchar(255)")
    private String image;

    @Column(name = "Description", columnDefinition = "nvarchar(50) not null")
    private String description;

    @Column(name = "EnteredDate", columnDefinition = "date not null")
    @Temporal(TemporalType.DATE)
    private Date enteredDate;

    @Column(name = "Status", columnDefinition = "nvarchar(50) not null")
    private String status;
    
    private Long categoryid;
    @ManyToOne
    @JoinColumn(name = "CategoryId", referencedColumnName = "CategoryId", insertable = false, updatable = false)
    private Category category;
}

