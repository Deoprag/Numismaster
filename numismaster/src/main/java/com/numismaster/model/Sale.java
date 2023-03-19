package com.numismaster.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = "tb_sale")
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "total_price")
	private float totalPrice;
	
	@Column(name = "sale_date")
	private Date saleDate;
	
	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private User buyerId;
	
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private User sellerId;
	
    @OneToMany(mappedBy = "sale")
    private List<CoinUserSale> coinSale;

}
