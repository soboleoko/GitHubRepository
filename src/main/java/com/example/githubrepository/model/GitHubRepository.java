package com.example.githubrepository.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "github_repository")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GitHubRepository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "owner")
    private String owner;
    @Column(name = "repository_name")
    private String repositoryName;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "description")
    private String description;
    @Column(name = "clone_url")
    private String cloneUrl;
    @Column(name = "stars")
    private int stars;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GitHubRepository)) return false;
        GitHubRepository gitHubRepository = (GitHubRepository) o;
        return id != null && id.equals(gitHubRepository.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void updateData(UpdateGitHubRepository updateGitHubRepository) {
        this.setFullName(updateGitHubRepository.getFullName());
        this.setDescription(updateGitHubRepository.getDescription());
        this.setCloneUrl(updateGitHubRepository.getCloneUrl());
        this.setStars(updateGitHubRepository.getStars());
        this.setCreatedAt(updateGitHubRepository.getCreatedAt());
        this.setOwner(updateGitHubRepository.getOwner());
        this.setRepositoryName(updateGitHubRepository.getRepositoryName());
    }
}
