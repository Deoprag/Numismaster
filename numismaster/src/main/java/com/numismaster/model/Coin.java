package com.numismaster.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Entity(name = "TB_Coin")
public class Coin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Column(name = "denomination", nullable = false)
	private Float denomination;

	@Column(name = "weight", nullable = false)
	private Float weight;

	@Column(name = "diameter", nullable = false)
	private Float diameter;

	@Column(name = "thickness", nullable = false)
	private Float thickness;

    @Enumerated(EnumType.STRING)
	@Column(name = "rarity", nullable = false)
	private Rarity rarity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "edge_id", nullable = false)
	private Edge edge;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "TB_Coin_Material",
			joinColumns = @JoinColumn(name = "coin_id"),
			inverseJoinColumns = @JoinColumn(name = "material_id"))
	private List<Material> materials = new ArrayList<>();

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "TB_Coin_Shape",
			joinColumns = @JoinColumn(name = "coin_id"),
			inverseJoinColumns = @JoinColumn(name = "shape_id"))
	private List<Shape> shapes = new ArrayList<>();
}
