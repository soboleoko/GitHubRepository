package com.example.githubrepository.service;

import com.example.githubrepository.client.GitHubRepositoryClient;
import com.example.githubrepository.exception.GitHubRepositoryNotFoundException;
import com.example.githubrepository.mapper.GitHubRepositoryMapper;
import com.example.githubrepository.model.GitHubRepository;
import com.example.githubrepository.model.GitHubRepositoryDTO;
import com.example.githubrepository.model.GitHubRepositoryInfoDTO;
import com.example.githubrepository.model.UpdateGitHubRepository;
import com.example.githubrepository.repository.GitHubReposRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GitHubRepositoryService {
    private final GitHubRepositoryClient gitHubRepositoryClient;
    private final GitHubRepositoryMapper gitHubRepositoryMapper;
    private final GitHubReposRepository gitHubReposRepository;

    public GitHubRepositoryInfoDTO getGitHubRepository(String owner, String repositoryName) {
        GitHubRepositoryInfoDTO gitHubRepository = gitHubRepositoryClient.getGitHubRepository(owner, repositoryName);
        log.info("GitHubAPI returned {}", gitHubRepository);
        return gitHubRepository;
    }

    public GitHubRepositoryDTO saveGitHubRepository(String owner, String repositoryName) {
        GitHubRepository gitHubRepository = gitHubRepositoryMapper
                .mapToGitHubRepository(gitHubRepositoryClient.getGitHubRepository(owner, repositoryName));
        gitHubRepository.setOwner(owner);
        gitHubRepository.setRepositoryName(repositoryName);
        gitHubReposRepository.save(gitHubRepository);
        log.info("GitHubRepositoryService saved {}", gitHubRepository);
        return gitHubRepositoryMapper.mapToGitHubRepositoryDTO(gitHubRepository);
    }

    public GitHubRepositoryDTO getLocalGitHubRepository(String owner, String repositoryName) {
        GitHubRepository gitHubRepository = gitHubReposRepository.findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new GitHubRepositoryNotFoundException("The repository with such provided author or name does not exist",
                        HttpStatus.NOT_FOUND));
        log.info("GitHubRepositoryService got {} from data base", gitHubRepository);
        return gitHubRepositoryMapper.mapToGitHubRepositoryDTO(gitHubRepository);
    }

    @Transactional
    public GitHubRepositoryDTO updateLocalGitHubRepository(String owner, String repositoryName, UpdateGitHubRepository updateGitHubRepository) {
        GitHubRepository gitHubRepositoryByOwnerAndRepositoryName = gitHubReposRepository
                .findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new GitHubRepositoryNotFoundException("The repository with such provided author or name does not exist",
                        HttpStatus.NOT_FOUND));
        gitHubRepositoryByOwnerAndRepositoryName.updateData(updateGitHubRepository);
        log.info("GitHubRepositoryService updated repository with path {}/{}. New data: {}", owner, repositoryName, updateGitHubRepository);
        return gitHubRepositoryMapper.mapToGitHubRepositoryDTO(gitHubRepositoryByOwnerAndRepositoryName);
    }

    @Transactional
    public void deleteLocalGitHubRepository(String owner, String repositoryName) {
        GitHubRepository gitHubRepositoryByOwnerAndRepositoryName = gitHubReposRepository
                .findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new GitHubRepositoryNotFoundException("The repository with such provided author or name does not exist",
                        HttpStatus.NOT_FOUND));
        gitHubReposRepository.delete(gitHubRepositoryByOwnerAndRepositoryName);
        log.info("GitHubRepositoryService deleted {} from data base", gitHubRepositoryByOwnerAndRepositoryName);
    }
}

