package com.example.githubrepository.service;

import com.example.githubrepository.client.GitHubRepositoryClient;
import com.example.githubrepository.mapper.GitHubRepositoryMapper;
import com.example.githubrepository.model.GitHubRepositoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubRepositoryService {
    private final GitHubRepositoryClient gitHubRepositoryClient;
    private final GitHubRepositoryMapper gitHubRepositoryMapper;

    public GitHubRepositoryDTO getGitHubRepository(String owner, String repositoryName) {
       return gitHubRepositoryMapper.mapToGitHubRepositoryDTO(gitHubRepositoryClient.getGitHubRepository(owner, repositoryName));
    }
}
