package com.numismaster.service;

import com.numismaster.model.Coin;
import com.numismaster.repository.CoinRepository;

public class CoinService {
    private CoinRepository coinRepository;

    public CoinService(){
        coinRepository = new CoinRepository();
    }

    public boolean save(Coin coin){
        
        return false;
    }

    public boolean delete(Coin coin){
        return coinRepository.delete(coin.getId());
    }
}
