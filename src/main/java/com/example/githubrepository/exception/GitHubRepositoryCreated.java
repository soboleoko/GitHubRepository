package com.example.githubrepository.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GitHubRepositoryCreated extends Exception {
    private final HttpStatus httpStatus;

    public GitHubRepositoryCreated(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
