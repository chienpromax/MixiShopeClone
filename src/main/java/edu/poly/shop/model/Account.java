package edu.poly.shop.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {

	@Id
	@Column(columnDefinition = "nvarchar(30) not null")
	private String username;
	@Column(columnDefinition = "nvarchar(30) not null")
	private String password;
	private boolean role;
	@Column(columnDefinition = "nvarchar(50) not null")
	private String email;
}
