package com.example.githubrepository.configuration;

import com.example.githubrepository.client.CustomErrorDecoder;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubRepositoryClientConfiguration {

    @Bean
    public ErrorDecoder gitHubRepositoryErrorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
}
