package com.example.githubrepository.controller;

import com.example.githubrepository.model.GitHubRepositoryDTO;
import com.example.githubrepository.service.GitHubRepositoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repositories")
public class GitHubRepositoryController {
    private final GitHubRepositoryService gitHubRepositoryService;

    @GetMapping("/{owner}/{repositoryName}")
    public GitHubRepositoryDTO getGitHubRepository (@PathVariable String owner, @PathVariable String repositoryName) {
        return gitHubRepositoryService.getGitHubRepository(owner, repositoryName);
    }
}
