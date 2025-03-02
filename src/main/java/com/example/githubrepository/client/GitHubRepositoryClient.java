package com.example.githubrepository.client;

import com.example.githubrepository.model.GitHubRepositoryInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "apigithub", url = "https://api.github.com")
public interface GitHubRepositoryClient {
    @GetMapping("/repos/{owner}/{repo}")
    GitHubRepositoryInfoDTO getGitHubRepository(@PathVariable String owner, @PathVariable("repo") String repositoryName);
}
