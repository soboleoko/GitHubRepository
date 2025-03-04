package com.example.githubrepository.configuration;

import com.example.githubrepository.client.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

    @Bean
    public ErrorDecoder gitHubRepositoryErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
