package com.example.githubrepository.controller;

import com.example.githubrepository.model.GitHubRepositoryDTO;
import com.example.githubrepository.model.GitHubRepositoryInfoDTO;
import com.example.githubrepository.model.UpdateGitHubRepository;
import com.example.githubrepository.service.GitHubRepositoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GitHubRepositoryController {
    private final GitHubRepositoryService gitHubRepositoryService;

    @GetMapping("/repositories/{owner}/{repositoryName}")
    public GitHubRepositoryInfoDTO getGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        return gitHubRepositoryService.getGitHubRepository(owner, repositoryName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/repositories/{owner}/{repositoryName}")
    public GitHubRepositoryDTO saveGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        return gitHubRepositoryService.saveGitHubRepository(owner, repositoryName);
    }

    @GetMapping("/local/repositories/{owner}/{repositoryName}")
    public GitHubRepositoryDTO getLocalGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        return gitHubRepositoryService.getLocalGitHubRepository(owner, repositoryName);
    }

    @PutMapping("/local/repositories/{owner}/{repositoryName}")
    public GitHubRepositoryDTO updateLocalGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName,
                                                           @RequestBody @Valid UpdateGitHubRepository updateGitHubRepository) {
        return gitHubRepositoryService.updateLocalGitHubRepository(owner, repositoryName, updateGitHubRepository);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/local/repositories/{owner}/{repositoryName}")
    public void deleteLocalGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        gitHubRepositoryService.deleteLocalGitHubRepository(owner, repositoryName);
    }
}
