package com.example.exchange.controller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.*;
import java.util.*;

@Controller
public class PageController {

    @GetMapping (value = {"/", "/index.html"})
    public String home(Model mode) {
        mode.addAttribute("list", Arrays.asList("USD","HKD","GBP","AUD","CAD","SGD",
                "CHF", "JPY", "ZAR", "SEK", "NZD", "THB", "EUR", "CNY"));
        return "Homework";
    }

    @PostMapping("/change")
    public ResponseEntity<Object> chang(Model mode, String country) {
        List<String> rsp = new ArrayList<>();
        BufferedReader reader = null;
        InputStream inputStream = null ;
        try {
            ClassPathResource classPathResource = new ClassPathResource("datas/20210101-20211231-FXCRT.csv");
            reader = new BufferedReader(new InputStreamReader(classPathResource.getInputStream()));
            String tmp = null;
            Map<String, String> map = new HashMap<>();
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
            map.keySet().forEach( e -> rsp.add(map.get(e)));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(reader);
        }
        return ResponseEntity.status(HttpStatus.OK).body(rsp);
    }
}
