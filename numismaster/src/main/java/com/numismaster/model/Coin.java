package com.numismaster.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Entity(name = "tb_coin")
public class Coin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "denomination")
	private float denomination;
	
	@Column(name = "weight")
	private float weight;
	
	@Column(name = "diameter")
	private float diameter;
	
	@Column(name = "thickness")
	private float thickness;
		
	@Column(name = "rarity")
	@Enumerated(EnumType.STRING)
	private Rarity rarity;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	@ManyToOne
	@JoinColumn(name = "shape_id")
	private Shape shape;
    
	@OneToMany(mappedBy = "coin")
	private List<CoinUser> coinUser;
    
	@OneToMany(mappedBy = "coin")
	private List<CoinEdge> coinEdges;

	@OneToMany(mappedBy = "coin")
	private List<CoinMaterial> coinMaterial;
    
}
