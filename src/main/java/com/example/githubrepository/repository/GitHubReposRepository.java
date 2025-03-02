package com.example.githubrepository.repository;

import com.example.githubrepository.model.GitHubRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GitHubReposRepository extends JpaRepository<GitHubRepository, Long> {
    Optional<GitHubRepository> findGitHubRepositoryByOwnerAndRepositoryName(String owner, String repositoryName);
}
