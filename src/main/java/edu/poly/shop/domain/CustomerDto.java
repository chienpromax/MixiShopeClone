package edu.poly.shop.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Integer customerId;
    private String fullName;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registeredDate;
    private String tinh;
    private String quan;
    private String xa;
    private String address;
    private String note;
    private String username;

    private Boolean isEdit = false;
}
