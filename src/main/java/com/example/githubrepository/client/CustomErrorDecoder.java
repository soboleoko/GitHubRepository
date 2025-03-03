package com.example.githubrepository.client;

import com.example.githubrepository.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()) {
            case 200:
                return new GitHubRepositoryOK("Successful Repository operation", HttpStatus.OK);
            case 201:
                return new GitHubRepositoryCreated("Repository has been created", HttpStatus.CREATED);
            case 204:
                return new GitHubRepositoryNoContent("No body", HttpStatus.NO_CONTENT);
            case 403:
                return new GitHubRepositoryForbidden("Logged user has no access" , HttpStatus.FORBIDDEN);
            case 404:
                return new GitHubRepositoryNotFoundException("The repository with such provided author or name does not exist", HttpStatus.NOT_FOUND);
            default: return new Exception("Generic error");
        }
    }
}
