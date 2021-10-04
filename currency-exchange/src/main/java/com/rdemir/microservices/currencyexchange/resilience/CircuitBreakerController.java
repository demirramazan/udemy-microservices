package com.rdemir.microservices.currencyexchange.resilience;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
//    @Retry(name = "sample-api",fallbackMethod = "harcodedResponse")
//    @CircuitBreaker(name = "default", fallbackMethod = "harcodedResponse")
//    @RateLimiter(name="default")
//    @Bulkhead(name = "default")
    @Bulkhead(name = "sample-api")
    public String sampleApi() {
        logger.info("Sample api call received");
//        ResponseEntity<String> resposeEntity = new RestTemplate()
//                .getForEntity("http://localhost:8080/some-dummy-url", String.class);
//        return resposeEntity.getBody();
        return "Sample API";
    }

    public String harcodedResponse(Exception exception) {
        return "fallback response " + exception.getMessage();
    }
}
