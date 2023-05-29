package com.xjdl.study.restTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(5000);
        clientHttpRequestFactory.setConnectTimeout(10000);
        return new RestTemplate(clientHttpRequestFactory);
    }
}
