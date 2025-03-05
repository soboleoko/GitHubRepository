package com.example.githubrepository.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GitHubRepositoryOK extends Exception {
    private final HttpStatus httpStatus;

    public GitHubRepositoryOK(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
