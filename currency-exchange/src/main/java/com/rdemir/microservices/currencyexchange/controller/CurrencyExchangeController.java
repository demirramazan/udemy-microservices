package com.rdemir.microservices.currencyexchange.controller;

import com.rdemir.microservices.currencyexchange.model.CurrencyExchange;
import com.rdemir.microservices.currencyexchange.repository.CurrencyExchangeRepository;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@RestController
public class CurrencyExchangeController {
    //localhost:8000/currency-exchange/from/USD/to/INR
//localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
    private final Environment environment;
    private final CurrencyExchangeRepository repository;
    private static UUID uuid = UUID.randomUUID();

    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository repository) {
        this.environment = environment;
        this.repository = repository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
        if (currencyExchange == null) {
            throw new RuntimeException("Unable to Find data for" + from + " to " + to);
        }
//        CurrencyExchange exchange = new CurrencyExchange(1001, from, to, BigDecimal.valueOf(50));
        String port = environment.getProperty("local.server.port");
        environment.getProperty("local.server.host");
        String hostName = "";
        try {
            hostName = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        currencyExchange.setEnvironment(hostName + ":" + port + " --guid:" + uuid);
        return currencyExchange;
    }

}
