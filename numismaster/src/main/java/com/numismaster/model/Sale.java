package com.numismaster.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
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

        @Column(name = "price", nullable = false)
        private Float price;

        @Column(name = "sale_date", nullable = false, updatable = false)
        private LocalDateTime saleDate = LocalDateTime.now();

        @ManyToOne
        @JoinColumn(name = "buyer_id", nullable = false)
        private User buyer;

        @ManyToOne
        @JoinColumn(name = "seller_id", nullable = false)
        private User seller;

        @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
        private List<CoinUserSale> coinUserSales;
}
