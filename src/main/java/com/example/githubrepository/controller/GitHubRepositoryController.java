package com.example.githubrepository.controller;

import com.example.githubrepository.model.GitHubRepositoryDTO;
import com.example.githubrepository.model.GitHubRepositoryInfoDTO;
import com.example.githubrepository.model.UpdateGitHubRepository;
import com.example.githubrepository.service.GitHubRepositoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GitHubRepositoryController {
    private final GitHubRepositoryService gitHubRepositoryService;

    @GetMapping("/repositories/{owner}/{repositoryName}")
    public GitHubRepositoryInfoDTO getGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("New request for endpoint GET/repositories/{}/{} logged", owner, repositoryName);
        return gitHubRepositoryService.getGitHubRepository(owner, repositoryName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/repositories/{owner}/{repositoryName}")
    public GitHubRepositoryDTO saveGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("New request for endpoint POST/repositories/{}/{} logged", owner, repositoryName);
        return gitHubRepositoryService.saveGitHubRepository(owner, repositoryName);
    }

    @GetMapping("/local/repositories/{owner}/{repositoryName}")
    public GitHubRepositoryDTO getLocalGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("New request for endpoint GET/local/repositories/{}/{} logged", owner, repositoryName);

        return gitHubRepositoryService.getLocalGitHubRepository(owner, repositoryName);
    }

    @PutMapping("/local/repositories/{owner}/{repositoryName}")
    public GitHubRepositoryDTO updateLocalGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName,
                                                           @RequestBody @Valid UpdateGitHubRepository updateGitHubRepository) {
        log.info("New request for endpoint PUT/local/repositories/{}/{} logged with body containing UpdateGitHubRepository", owner, repositoryName);

        return gitHubRepositoryService.updateLocalGitHubRepository(owner, repositoryName, updateGitHubRepository);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/local/repositories/{owner}/{repositoryName}")
    public void deleteLocalGitHubRepository(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("New request for endpoint DELETE/local/repositories/{}/{} logged", owner, repositoryName);

        gitHubRepositoryService.deleteLocalGitHubRepository(owner, repositoryName);
    }
}
