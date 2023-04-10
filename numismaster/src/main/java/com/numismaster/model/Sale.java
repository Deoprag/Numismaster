package com.numismaster.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

        @Column(name = "total_price", nullable = false)
        private Float totalPrice;

        @Column(name = "sale_date", nullable = false, updatable = false)
        @Temporal(TemporalType.TIMESTAMP)
        private Date saleDate;

        @ManyToOne(fetch = FetchType.LAZY)
        @Column(name = "buyer_id", nullable = false)
        private User buyer;

        @ManyToOne(fetch = FetchType.LAZY)
        @Column(name = "seller_id", nullable = false)
        private User seller;
}
