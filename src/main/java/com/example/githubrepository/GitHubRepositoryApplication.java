package com.example.githubrepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy
public class GitHubRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubRepositoryApplication.class, args);
    }
}
