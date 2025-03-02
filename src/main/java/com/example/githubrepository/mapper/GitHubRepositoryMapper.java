package com.example.githubrepository.mapper;

import com.example.githubrepository.model.GitHubRepository;
import com.example.githubrepository.model.GitHubRepositoryInfoDTO;
import com.example.githubrepository.model.GitHubRepositoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GitHubRepositoryMapper {
    GitHubRepository mapToGitHubRepository(GitHubRepositoryInfoDTO gitHubRepositoryInfo);

    GitHubRepositoryDTO mapToGitHubRepositoryDTO(GitHubRepository gitHubRepository);
}
