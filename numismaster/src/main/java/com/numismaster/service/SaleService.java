package com.numismaster.service;

import com.numismaster.model.CoinUser;
import com.numismaster.model.Sale;
import com.numismaster.repository.SaleRepository;

public class SaleService {
    private SaleRepository saleRepository;

    public SaleService() {
        saleRepository = new SaleRepository();
    }

    public boolean coinSale(Sale sale, CoinUser coinUser){
        return saleRepository.coinSale(sale, coinUser);
    }
}
