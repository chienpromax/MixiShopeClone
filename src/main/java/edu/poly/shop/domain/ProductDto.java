package edu.poly.shop.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

	private Long productid;
	private String name;
	private Integer quantity;
	private Double price;
	private String image;
	private String description;
	private Double discount;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enteredDate;
	private String status;
	private Long categoryid;
	
	private Boolean isEdit = false;
	private MultipartFile imageFile;
}
