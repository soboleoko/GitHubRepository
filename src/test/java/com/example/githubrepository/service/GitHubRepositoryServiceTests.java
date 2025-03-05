package com.example.githubrepository.service;

import com.example.githubrepository.client.GitHubRepositoryClient;
import com.example.githubrepository.exception.GitHubRepositoryNotFoundException;
import com.example.githubrepository.mapper.GitHubRepositoryMapper;
import com.example.githubrepository.model.GitHubRepository;
import com.example.githubrepository.model.GitHubRepositoryDTO;
import com.example.githubrepository.model.GitHubRepositoryInfoDTO;
import com.example.githubrepository.model.UpdateGitHubRepository;
import com.example.githubrepository.repository.GitHubReposRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

public class GitHubRepositoryServiceTests {
    GitHubReposRepository gitHubReposRepository;
    GitHubRepositoryMapper gitHubRepositoryMapper;
    GitHubRepositoryClient gitHubRepositoryClient;
    GitHubRepositoryService gitHubRepositoryService;

    @BeforeEach
    void setUp() {
        this.gitHubReposRepository = Mockito.mock(GitHubReposRepository.class);
        this.gitHubRepositoryMapper = Mockito.mock(GitHubRepositoryMapper.class);
        this.gitHubRepositoryClient = Mockito.mock(GitHubRepositoryClient.class);
        this.gitHubRepositoryService = new GitHubRepositoryService(gitHubRepositoryClient, gitHubRepositoryMapper, gitHubReposRepository);
    }

    @Test
    void getGitHubRepository_successfulGet_repoReturned() {
        //given
        String owner = "autor";
        String repositoryName = "nazwaRepo";
        GitHubRepositoryInfoDTO gitHubRepositoryInfoDTO = new GitHubRepositoryInfoDTO("nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59));
        Mockito.when(gitHubRepositoryClient.getGitHubRepository(owner, repositoryName)).thenReturn(gitHubRepositoryInfoDTO);

        //when
        GitHubRepositoryInfoDTO result = gitHubRepositoryService.getGitHubRepository(owner, repositoryName);
        //then
        Assertions.assertEquals("nazwa", result.getFullName());
        Assertions.assertEquals("opis", result.getDescription());
        Assertions.assertEquals("url", result.getCloneUrl());
        Assertions.assertEquals(0, result.getStars());
        Assertions.assertEquals(LocalDateTime.of(2025, 1, 1, 23, 59), result.getCreatedAt());
    }

    @Test
    void saveGitHubRepository_successfulPost_repoReturned() {
        //given
        String owner = "autor";
        String repositoryName = "nazwaRepo";
        GitHubRepositoryInfoDTO gitHubRepositoryInfoDTO = new GitHubRepositoryInfoDTO("nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59));
        GitHubRepository gitHubRepository = new GitHubRepository(null, owner, repositoryName, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59));
        GitHubRepository savedRepository = new GitHubRepository(1L, owner, repositoryName, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59));
        GitHubRepositoryDTO gitHubRepositoryDTO = new GitHubRepositoryDTO(1L, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59), owner, repositoryName);
        Mockito.when(gitHubRepositoryClient.getGitHubRepository(owner, repositoryName)).thenReturn(gitHubRepositoryInfoDTO);
        Mockito.when(gitHubRepositoryMapper.mapToGitHubRepository(gitHubRepositoryInfoDTO)).thenReturn(savedRepository);
        Mockito.when(gitHubReposRepository.save(gitHubRepository)).thenReturn(savedRepository);
        Mockito.when(gitHubRepositoryMapper.mapToGitHubRepositoryDTO(savedRepository)).thenReturn(gitHubRepositoryDTO);

        //when
        GitHubRepositoryDTO result = gitHubRepositoryService.saveGitHubRepository(owner, repositoryName);
        //then
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("nazwa", result.getFullName());
        Assertions.assertEquals("opis", result.getDescription());
        Assertions.assertEquals("url", result.getCloneUrl());
        Assertions.assertEquals(0, result.getStars());
        Assertions.assertEquals(LocalDateTime.of(2025, 1, 1, 23, 59), result.getCreatedAt());
        Assertions.assertEquals(owner, result.getOwner());
        Assertions.assertEquals(repositoryName, result.getRepositoryName());
    }

    @Test
    void getLocalGitHubRepository_successfulGet_repoReturned() {
        //given
        String owner = "autor";
        String repositoryName = "nazwaRepo";
        GitHubRepository savedRepository = new GitHubRepository(1L, owner, repositoryName, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59));
        GitHubRepositoryDTO gitHubRepositoryDTO = new GitHubRepositoryDTO(1L, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59), owner, repositoryName);
        Mockito.when(gitHubReposRepository.findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)).thenReturn(Optional.of(savedRepository));
        Mockito.when(gitHubRepositoryMapper.mapToGitHubRepositoryDTO(savedRepository)).thenReturn(gitHubRepositoryDTO);
        //when
        GitHubRepositoryDTO result = gitHubRepositoryService.getLocalGitHubRepository(owner, repositoryName);
        //then
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("nazwa", result.getFullName());
        Assertions.assertEquals("opis", result.getDescription());
        Assertions.assertEquals("url", result.getCloneUrl());
        Assertions.assertEquals(0, result.getStars());
        Assertions.assertEquals(LocalDateTime.of(2025, 1, 1, 23, 59), result.getCreatedAt());
        Assertions.assertEquals(owner, result.getOwner());
        Assertions.assertEquals(repositoryName, result.getRepositoryName());
    }

    @Test
    void getLocalGitHubRepository_throwGitHubRepositoryNotFoundException_exceptionThrown() {
        //given
        String owner = "autor";
        String repositoryName = "nazwaRepo";
        Mockito.when(gitHubReposRepository.findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)).thenReturn(Optional.empty());
        //when
        GitHubRepositoryNotFoundException result = Assertions.assertThrows(GitHubRepositoryNotFoundException.class, () -> gitHubRepositoryService.getLocalGitHubRepository(owner, repositoryName));
        //then
        Assertions.assertEquals("The repository with such provided author or name does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void updateLocalGitHubRepository_successfulPut_repoReturned() {
        //given
        String owner = "autor";
        String repositoryName = "nazwaRepo";
        GitHubRepository savedRepository = new GitHubRepository(1L, owner, repositoryName, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59));
        UpdateGitHubRepository updateGitHubRepository = new UpdateGitHubRepository("nowaNazwa", "nowyOpis", "nowyUrl", 13, LocalDateTime.of(2024, 1, 1, 23, 59), "nowyAutor", "nowaNazwaRepo");
        GitHubRepository updatedRepository = new GitHubRepository(1L, "nowyAutor", "nowaNazwaRepo", "nowaNazwa", "nowyOpis", "nowyUrl", 13, LocalDateTime.of(2024, 1, 1, 23, 59));
        GitHubRepositoryDTO gitHubRepositoryDTO = new GitHubRepositoryDTO(1L, "nowaNazwa", "nowyOpis", "nowyUrl", 13, LocalDateTime.of(2024, 1, 1, 23, 59), "nowyAutor", "nowaNazwaRepo");
        Mockito.when(gitHubReposRepository.findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)).thenReturn(Optional.of(savedRepository));
        Mockito.when(gitHubReposRepository.save(updatedRepository)).thenReturn(updatedRepository);
        Mockito.when(gitHubRepositoryMapper.mapToGitHubRepositoryDTO(updatedRepository)).thenReturn(gitHubRepositoryDTO);
        //when
        GitHubRepositoryDTO result = gitHubRepositoryService.updateLocalGitHubRepository(owner, repositoryName, updateGitHubRepository);
        //then
        Assertions.assertEquals("nowaNazwa", result.getFullName());
        Assertions.assertEquals("nowyOpis", result.getDescription());
        Assertions.assertEquals("nowyUrl", result.getCloneUrl());
        Assertions.assertEquals(13, result.getStars());
        Assertions.assertEquals(LocalDateTime.of(2024, 1, 1, 23, 59), result.getCreatedAt());
        Assertions.assertEquals("nowyAutor", result.getOwner());
        Assertions.assertEquals("nowaNazwaRepo", result.getRepositoryName());
    }

    @Test
    void updateLocalGitHubRepository_throwGitHubRepositoryNotFoundException_exceptionThrown() {
        //given
        String owner = "autor";
        String repositoryName = "nazwaRepo";
        UpdateGitHubRepository updateGitHubRepository = new UpdateGitHubRepository();
        Mockito.when(gitHubReposRepository.findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)).thenReturn(Optional.empty());
        //when
        GitHubRepositoryNotFoundException result = Assertions.assertThrows(GitHubRepositoryNotFoundException.class, () -> gitHubRepositoryService.updateLocalGitHubRepository(owner, repositoryName, updateGitHubRepository));
        //then
        Assertions.assertEquals("The repository with such provided author or name does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }

    @Test
    void deleteLocalGitHubRepository_successfulDelete_repoDeleted() {
        //given
        String owner = "autor";
        String repositoryName = "nazwaRepo";
        GitHubRepository savedRepository = new GitHubRepository(1L, owner, repositoryName, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59));
        Mockito.when(gitHubReposRepository.findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)).thenReturn(Optional.of(savedRepository));
        //when
        gitHubRepositoryService.deleteLocalGitHubRepository(owner, repositoryName);
        //then
        Mockito.verify(gitHubReposRepository).delete(savedRepository);
    }

    @Test
    void deleteLocalGitHubRepository_throwGitHubRepositoryNotFoundException_exceptionThrown() {
        //given
        String owner = "autor";
        String repositoryName = "nazwaRepo";
        Mockito.when(gitHubReposRepository.findGitHubRepositoryByOwnerAndRepositoryName(owner, repositoryName)).thenReturn(Optional.empty());
        //when
        GitHubRepositoryNotFoundException result = Assertions.assertThrows(GitHubRepositoryNotFoundException.class, () -> gitHubRepositoryService.deleteLocalGitHubRepository(owner, repositoryName));
        //then
        Assertions.assertEquals("The repository with such provided author or name does not exist", result.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, result.getHttpStatus());
    }
}

