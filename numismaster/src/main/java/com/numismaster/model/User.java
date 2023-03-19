package com.numismaster.model;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "gender")
	private char gender;
	
	@Column(name = "birth_date")
	private Date birthDate;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "cpf")
	private String cpf;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;

	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private Type type;

	@Column(name = "is_blocked")
	private boolean isBlocked;

	@Column(name = "profile_photo")
	private Blob profilePhoto;
	
	@Column(name = "registration_date")
	private Date registrationDate;
	
	@OneToMany(mappedBy = "user")
	private List<CoinUser> coinUser;
}
