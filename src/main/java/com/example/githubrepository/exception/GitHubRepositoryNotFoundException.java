package com.example.githubrepository.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GitHubRepositoryNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

    public GitHubRepositoryNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
