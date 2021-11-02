package com.rdemir.microservices.currencyconversion.controller;

import com.rdemir.microservices.currencyconversion.client.CurrencyExchangeFeignClient;
import com.rdemir.microservices.currencyconversion.model.CurrencyConversion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {
    /*
    /currency-conversion/from/USD/to/INR/quantity/10
     */
    private final CurrencyExchangeFeignClient exchangeFeignClient;

    public CurrencyConversionController(final CurrencyExchangeFeignClient exchangeFeignClient) {
        this.exchangeFeignClient = exchangeFeignClient;
    }

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
                                                          @PathVariable String to,
                                                          @PathVariable Integer quantity) {
        HashMap<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> exchangeResponse = new RestTemplate()
                .getForEntity("http://currency-exchange:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);

        CurrencyConversion currencyConversion = exchangeResponse.getBody();

        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
                currencyConversion.getConversionMultiple(),
                currencyConversion.getConversionMultiple().multiply(BigDecimal.valueOf(quantity)),
                currencyConversion.getEnvironment() + " rest template");
    }

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
                                                               @PathVariable String to,
                                                               @PathVariable Integer quantity) {

        CurrencyConversion currencyConversion = exchangeFeignClient.retrieveExchangeValue(from, to);
        String hostName = "";
        try {
            hostName = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
        }
        return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
                currencyConversion.getConversionMultiple(),
                currencyConversion.getConversionMultiple().multiply(BigDecimal.valueOf(quantity)),
                currencyConversion.getEnvironment() + " feign" + " hostname:" + hostName);
    }
}
