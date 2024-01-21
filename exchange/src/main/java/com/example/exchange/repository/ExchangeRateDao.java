package com.example.exchange.repository;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class ExchangeRateDao {

    public String getExchangeRate(String country){
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("datas/20210101-20211231-FXCRT.csv");
            reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
            String tmp = null;
            Map<String, String> map = new TreeMap<>();
            //tmp 20211230,USD,27.63,27.73
            while ((tmp = reader.readLine()) != null){
                if(tmp.indexOf("日期")>0){continue;}//頭筆跳過
                if(tmp.indexOf(country)<=0){continue;}//非選擇國跳過
                String month = tmp.split(",")[0].substring(0,6);
                if(!map.containsKey(month)){
                    String exchange = tmp.split(",")[2];
                    map.put(month, exchange);
                }
            }
            map.keySet().forEach( e -> sb.append(map.get(e)).append("\n"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
