package com.numismaster.service;

import java.util.List;

import com.numismaster.model.CoinUserSale;
import com.numismaster.model.User;
import com.numismaster.repository.CoinUserSaleRepository;

public class CoinUserSaleService {
    
    CoinUserSaleRepository coinUserSaleRepository;

    public CoinUserSaleService(){
        coinUserSaleRepository = new CoinUserSaleRepository();
    }

    public List<CoinUserSale> findAll(){
        return coinUserSaleRepository.findAll();
    }

    public List<CoinUserSale> findTransactionsByUser(User user){
        return coinUserSaleRepository.findTransactionsByUser(user.getId());
    }
}
