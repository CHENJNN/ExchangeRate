package com.example.exchange.service;

import com.example.exchange.repository.ExchangeRateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateDao exchangeRateDao;

    public List<String> getExchangeRate(String country){
        List<String> rsp = new ArrayList<>();
        try {
            String exchangeRate = exchangeRateDao.getExchangeRate(country);
            String[] exchangeRates = exchangeRate.split("\n");
            Arrays.asList(exchangeRates).forEach(e -> rsp.add(e));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsp;
    }
}
