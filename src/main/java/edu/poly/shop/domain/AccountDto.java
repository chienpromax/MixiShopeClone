package edu.poly.shop.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

	private String username;
	private String password;
	private boolean role;
	private String email;

	private Boolean isEdit = false;
}
