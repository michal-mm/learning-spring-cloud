package com.in28minutes.microservices.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter (RouteLocatorBuilder builder) {
		return builder.routes()
				.route(req -> req.path("/get")
								.filters(f -> f.addRequestHeader("MyHeader", "Michal")
												.addRequestParameter("Param", "Param-Value"))
								.uri("http://httpbin.org:80"))
				.route(req -> req.path("/currency-exchange/**")
								.uri("lb://currency-exchange"))
				.route(req -> req.path("/currency-exchange-feign/**")
						.filters(f -> f.rewritePath("/currency-exchange-feign/(?<segment>.*)", 
													"/currency-exchange/${segment}"))
						.uri("lb://currency-exchange"))
				.route(req -> req.path("/currency-conversion/**")
								.uri("lb://currency-conversion"))
				.route(req -> req.path("/currency-conversion-feign/**")
								.uri("lb://currency-conversion"))
				.route(req -> req.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)", 
													"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}
}
