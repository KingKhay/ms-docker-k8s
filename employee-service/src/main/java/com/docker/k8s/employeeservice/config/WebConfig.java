package com.docker.k8s.employeeservice.config;

import com.docker.k8s.employeeservice.client.DepartmentExchangeProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class WebConfig {

    @Value("${k8s.department.service}")
    private String departmentServiceBaseUrl;

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl(departmentServiceBaseUrl)
                .build();
    }

    @Bean
    public DepartmentExchangeProxy getDepartmentExchangeProxy(){
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient()))
                .build();

        return httpServiceProxyFactory.createClient(DepartmentExchangeProxy.class);
    }
}
