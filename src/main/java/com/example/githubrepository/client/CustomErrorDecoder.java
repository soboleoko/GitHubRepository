package com.example.githubrepository.client;

import com.example.githubrepository.exception.*;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException feignException = FeignException.errorStatus(methodKey, response);
        RetryableException retryableException = new RetryableException(response.status(), feignException.getMessage(), response.request().httpMethod(), feignException,
                1000L, response.request());
        return switch (response.status()) {
            case 404 ->
                    new GitHubRepositoryNotFoundException("The repository with such provided author or name does not exist", HttpStatus.NOT_FOUND);
            case 501, 502, 503, 504 ->
                 retryableException;
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
