package com.numismaster.model;

import java.sql.Blob;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "gender", nullable = false)
	private Gender gender;

	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "cpf", unique = true, nullable = false, length = 11)
	private String cpf;

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "type", nullable = false)
	private Type type = Type.Comum;

	@Column(name = "is_blocked", nullable = false)
	private boolean isBlocked = true;

	@Column(name = "profile_photo", nullable = false)
	private Blob profilePhoto;

	@Column(name = "registration_date", nullable = false)
	private LocalDateTime registrationDate = LocalDateTime.now();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "tb_coin_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "coin_id"))
	private List<Coin> coins = new ArrayList<>();

	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Sale> sellList;

	@OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
	private List<Sale> buyList;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserRequest> userRequests;
}
