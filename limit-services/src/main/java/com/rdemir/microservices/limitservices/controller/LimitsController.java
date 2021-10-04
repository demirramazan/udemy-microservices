package com.rdemir.microservices.limitservices.controller;

import com.rdemir.microservices.limitservices.configuration.LimitsConfiguration;
import com.rdemir.microservices.limitservices.model.Limits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {
    private final LimitsConfiguration configuration;

    public LimitsController(LimitsConfiguration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("/limits")
    public Limits retrieveLimits() {
        return new Limits(configuration.getMin(), configuration.getMax());
    }
}
