package com.example.githubrepository.client;

import com.example.githubrepository.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 404:
                return new GitHubRepositoryNotFoundException("The repository with such provided author or name does not exist", HttpStatus.NOT_FOUND);
            default:
                return errorDecoder.decode(methodKey, response);
        }
    }
}
