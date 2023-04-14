package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Coin;
import com.numismaster.model.CoinUser;
import com.numismaster.repository.CoinUserRepository;

public class CoinUserService {
    private CoinUserRepository coinUserRepository;

    public CoinUserService(){
        coinUserRepository = new CoinUserRepository();
    }

    public List<CoinUser> findUsersByCoin(Coin coin){
        return coinUserRepository.findUsersByCoin(coin.getId());
    }
}
