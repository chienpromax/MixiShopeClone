package edu.poly.shop.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId")
    private Integer customerId;

    @Column(name = "FullName", columnDefinition = "nvarchar(50) not null")
    private String fullName;

    @Column(name = "Phone", columnDefinition = "nvarchar(50) not null")
    private String phone;

    @Column(name = "RegisteredDate", columnDefinition = "date not null")
    @Temporal(TemporalType.DATE)
    private Date registeredDate;

    @Column(name = "Tinh", columnDefinition = "nvarchar(50) not null")
    private String tinh;

    @Column(name = "Quan", columnDefinition = "nvarchar(50) not null")
    private String quan;

    @Column(name = "Xa", columnDefinition = "nvarchar(50) not null")
    private String xa;

    @Column(name = "Address", columnDefinition = "nvarchar(100)")
    private String address;

    @Column(name = "Note", columnDefinition = "nvarchar(100)")
    private String note;

    @Column(name = "Username", columnDefinition = "nvarchar(30)")
    private String username;

    @ManyToOne
    @JoinColumn(name = "Username", referencedColumnName = "Username", insertable = false, updatable = false)
    private Account account;
}
