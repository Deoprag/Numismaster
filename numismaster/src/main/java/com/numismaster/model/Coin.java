package com.numismaster.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

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

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "denomination", nullable = false)
	private int denomination;

	@Column(name = "weight", nullable = false)
	private Float weight;

	@Column(name = "diameter", nullable = false)
	private Float diameter;

	@Column(name = "thickness", nullable = false)
	private Float thickness;

	@Column(name = "rarity", nullable = false)
	private Rarity rarity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "TB_Coin_Edge", joinColumns = @JoinColumn(name = "coin_id"), inverseJoinColumns = @JoinColumn(name = "edge_id"))
	private List<Edge> edges = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "TB_Coin_Material", joinColumns = @JoinColumn(name = "coin_id"), inverseJoinColumns = @JoinColumn(name = "material_id"))
	private List<Material> materials = new ArrayList<>();

	@ManyToMany(cascade = { CascadeType.MERGE })
	@JoinTable(name = "TB_Coin_Shape", joinColumns = @JoinColumn(name = "coin_id"), inverseJoinColumns = @JoinColumn(name = "shape_id"))
	private List<Shape> shapes = new ArrayList<>();
}
