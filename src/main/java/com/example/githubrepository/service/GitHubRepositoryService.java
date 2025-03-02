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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubRepositoryService {
    private final GitHubRepositoryClient gitHubRepositoryClient;
    private final GitHubRepositoryMapper gitHubRepositoryMapper;
    private final GitHubReposRepository gitHubReposRepository;

    public GitHubRepositoryInfoDTO getGitHubRepository(String owner, String repositoryName) {
        return gitHubRepositoryClient.getGitHubRepository(owner, repositoryName);
    }

    public GitHubRepositoryDTO saveGitHubRepository(String owner, String repositoryName) {
        GitHubRepository gitHubRepository = gitHubRepositoryMapper
                .mapToGitHubRepository(gitHubRepositoryClient.getGitHubRepository(owner, repositoryName));
        gitHubRepository.setOwner(owner);
        gitHubRepository.setRepositoryName(repositoryName);
        gitHubReposRepository.save(gitHubRepository);
        return gitHubRepositoryMapper.mapToGitHubRepositoryDTO(gitHubRepository);
    }

    public GitHubRepositoryDTO getLocalGitHubRepository(String owner, String repositoryName) {
        GitHubRepository gitHubRepository = gitHubReposRepository.findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new GitHubRepositoryNotFoundException("Nie znaleziono repozytorium o podanej nazwie lub jego autora",
                        HttpStatus.NOT_FOUND));
        return gitHubRepositoryMapper.mapToGitHubRepositoryDTO(gitHubRepository);
    }

    @Transactional
    public GitHubRepositoryDTO updateLocalGitHubRepository(String owner, String repositoryName, UpdateGitHubRepository updateGitHubRepository) {
        GitHubRepository gitHubRepositoryByOwnerAndRepositoryName = gitHubReposRepository
                .findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new GitHubRepositoryNotFoundException("Nie znaleziono repozytorium o podanej nazwie lub jego autora",
                        HttpStatus.NOT_FOUND));
        gitHubRepositoryByOwnerAndRepositoryName.updateData(updateGitHubRepository);
        return gitHubRepositoryMapper.mapToGitHubRepositoryDTO(gitHubRepositoryByOwnerAndRepositoryName);
    }

    @Transactional
    public void deleteLocalGitHubRepository(String owner, String repositoryName) {
        GitHubRepository gitHubRepositoryByOwnerAndRepositoryName = gitHubReposRepository
                .findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)
                .orElseThrow(() -> new GitHubRepositoryNotFoundException("Nie znaleziono repozytorium o podanej nazwie lub jego autora",
                        HttpStatus.NOT_FOUND));
        gitHubReposRepository.delete(gitHubRepositoryByOwnerAndRepositoryName);
    }
}

