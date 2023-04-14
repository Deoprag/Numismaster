package com.numismaster.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = "tb_coin_user_sale")
public class CoinUserSale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "coin_user_id", nullable = false)
	private CoinUser coinUser;

	@ManyToOne
	@JoinColumn(name = "sale_id", nullable = false)
	private Sale sale;
	
	@Column(name = "price", nullable = false)
	private float price;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
}
