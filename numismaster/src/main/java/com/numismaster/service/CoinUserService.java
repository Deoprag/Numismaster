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

    public List<CoinUser> findAll(){
        return coinUserRepository.findAll();
    }

    public CoinUser findById(int id){
        return coinUserRepository.findById(id);
    }

    public List<CoinUser> findAllForSale(){
        return coinUserRepository.findAllForSale();
    }
}
