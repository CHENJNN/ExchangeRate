package com.example.exchange.controller;

import com.example.exchange.service.ExchangeRateService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@Log4j2
public class PageController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping (value = {"/", "/index.html"})
    public String home(Model mode) {
        mode.addAttribute("list", Arrays.asList("USD","HKD","GBP","AUD","CAD","SGD",
                "CHF", "JPY", "ZAR", "SEK", "NZD", "THB", "EUR", "CNY"));
        return "Homework";
    }

    @PostMapping("/change")
    public ResponseEntity<Object> change(Model mode, String country) {
        List<String> rsp = new ArrayList<>();
        try {
            if(StringUtils.isNotBlank(country)){
                rsp = exchangeRateService.getExchangeRate(country);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(rsp);
    }
}
