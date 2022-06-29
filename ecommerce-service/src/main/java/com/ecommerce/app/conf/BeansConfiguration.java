package com.ecommerce.app.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeansConfiguration {
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
