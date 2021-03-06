package com.rdemir.microservices.currencyconversion.client;

import com.rdemir.microservices.currencyconversion.model.CurrencyConversion;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "localhost:8000;localhost:8001")
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeFeignClient {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchangeValue(@PathVariable String from,
                                                    @PathVariable String to) ;
}
