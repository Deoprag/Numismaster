package com.numismaster.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_coin_user")
public class CoinUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "coin_id")
	private Coin coin;
	
	@Column(name = "year")
	private short year;

	@Column(name = "coin_condition")
	@Enumerated(EnumType.STRING)
	private CoinCondition coinCondition;
	
	@Column(name = "is_for_sale")
	private boolean isForSale;
	
	@Column(name = "price")
	private float price;
	
	@Column(name = "image_front")
	private Blob imageFront;
	
	@Column(name = "image_back")
	private Blob imageBack;
	
	@Column(name = "notes")
	private String notes;

}
