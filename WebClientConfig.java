package com.employeebuffer.departmentservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import com.employeebuffer.departmentservice.client.EmployeeClient;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;


@Configuration

public class WebClientConfig {
	
	@Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;


    @Bean
    public WebClient employeeWebClient() {
        return WebClient.builder()
                .baseUrl("http://employee")
                .filter(filterFunction)
                .build();
    }

    @Bean
    public EmployeeClient employeeClient() {
        HttpServiceProxyFactory httpServiceProxyFactory
                = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(employeeWebClient()))
                .build();
        return httpServiceProxyFactory.createClient(EmployeeClient.class);
    }

}
