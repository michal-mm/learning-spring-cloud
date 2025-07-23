package com.in28minutes.microservices.currency_exchange_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CircuitBreakerController {
	
	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")
	@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")
//	@RateLimiter(name = "default")
//	@Bulkhead(name = "default")
	public String sampleApi() {
		logger.info("Sample api call received");
		
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost/some-dummy-url", String.class);
		
		return forEntity.getBody();
//		return "sample-api";
	}
	
	public String hardcodedResponse(Exception ex) {
		logger.info("Fallback to hardcoded for sample-api: " + ex.getMessage());
		return "Fallback response for sample-api";
	}
}
