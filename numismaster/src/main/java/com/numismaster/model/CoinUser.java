package com.numismaster.model;

import java.sql.Blob;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coin_id", nullable = false)
	private Coin coin;

	@Column(name = "year", nullable = false)
	private short year;

	@Column(name = "coin_condition", nullable = false)
	private CoinCondition coinCondition;

	@Column(name = "is_for_sale", nullable = false)
	private boolean forSale;

	@Column(name = "price")
	private Float price;

	@Column(name = "image_front")
	private Blob imageFront;

	@Column(name = "image_back")
	private Blob imageBack;

	@Column(name = "notes", length = 200)
	private String notes;

	@OneToMany(mappedBy = "coinUser", cascade = CascadeType.ALL)
    private List<CoinUserSale> coinUserSales;
}
