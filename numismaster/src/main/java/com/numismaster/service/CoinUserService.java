package com.numismaster.service;

import java.util.List;

import com.numismaster.model.Coin;
import com.numismaster.model.CoinUser;
import com.numismaster.model.User;
import com.numismaster.repository.CoinUserRepository;

public class CoinUserService {
    private CoinUserRepository coinUserRepository;

    public CoinUserService(){
        coinUserRepository = new CoinUserRepository();
    }

    public boolean save(CoinUser coinUser){
        if(coinUser.getId() == 0){
            return coinUserRepository.insert(coinUser);
        } else {
            return coinUserRepository.update(coinUser);
        }
    }

    public boolean delete(CoinUser coinUser){
        return coinUserRepository.delete(coinUser.getId());
    }

    public List<CoinUser> findUsersByCoin(Coin coin){
        return coinUserRepository.findUsersByCoin(coin.getId());
    }

    public List<CoinUser> findAll(){
        return coinUserRepository.findAll();
    }

    public List<CoinUser> findAllByUser(User user){
        return coinUserRepository.findAllByUser(user.getId());
    }

    public CoinUser findById(int id){
        return coinUserRepository.findById(id);
    }

    public List<CoinUser> findAllForSale(){
        return coinUserRepository.findAllForSale();
    }

    public Long findHowManyCoinsByUser(User user){
        return coinUserRepository.findHowManyCoinsByUser(user.getId());
    }

    public CoinUser findRarestCoinByUser(User user){
        return coinUserRepository.findRarestCoinByUser(user.getId());
    }
}
