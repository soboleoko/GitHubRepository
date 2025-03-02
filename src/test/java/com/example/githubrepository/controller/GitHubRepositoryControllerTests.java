package com.example.githubrepository.controller;

import com.example.githubrepository.model.GitHubRepositoryDTO;
import com.example.githubrepository.model.GitHubRepositoryInfoDTO;
import com.example.githubrepository.model.UpdateGitHubRepository;
import com.example.githubrepository.service.GitHubRepositoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class GitHubRepositoryControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    @MockitoBean
    GitHubRepositoryService gitHubRepositoryService;

    @Test
    void getGitHubRepository_successfulGet_repoReturned() throws Exception {
        GitHubRepositoryInfoDTO gitHubRepositoryInfoDTO = new GitHubRepositoryInfoDTO("nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59));
        Mockito.when(gitHubRepositoryService.getGitHubRepository("autor", "nazwaRepo")).thenReturn(gitHubRepositoryInfoDTO);
        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{owner}/{repositoryName}", "autor", "nazwaRepo"))
                .andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.jsonPath("$.full_name").value("nazwa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("opis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clone_url").value("url"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stargazers_count").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_at").value("2025-01-01T23:59:00"));
    }

    @Test
    void saveGitHubRepository_successfulPost_repoReturned() throws Exception {
        GitHubRepositoryDTO gitHubRepositoryDTO = new GitHubRepositoryDTO(1L, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59), "autor", "nazwaRepo");
        Mockito.when(gitHubRepositoryService.saveGitHubRepository("autor", "nazwaRepo")).thenReturn(gitHubRepositoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/{owner}/{repositoryName}", "autor", "nazwaRepo"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("nazwa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("opis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloneUrl").value("url"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").value("2025-01-01T23:59:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value("autor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.repositoryName").value("nazwaRepo"));

    }

    @Test
    void getLocalGitHubRepository_successfulGet_repoReturned() throws Exception {
        GitHubRepositoryDTO gitHubRepositoryDTO = new GitHubRepositoryDTO(1L, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59), "autor", "nazwaRepo");
        Mockito.when(gitHubRepositoryService.getLocalGitHubRepository("autor", "nazwaRepo")).thenReturn(gitHubRepositoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/local/repositories/{owner}/{repositoryName}", "autor", "nazwaRepo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("nazwa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("opis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloneUrl").value("url"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").value("2025-01-01T23:59:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value("autor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.repositoryName").value("nazwaRepo"));
    }

    @Test
    void updateLocalGitHubRepository_successfulPut_repoReturned() throws Exception {
        UpdateGitHubRepository updateGitHubRepository = new UpdateGitHubRepository("nowaNazwa", "nowyOpis", "nowyUrl", 13, LocalDateTime.of(2024, 1, 1, 23, 59), "nowyAutor", "nowaNazwaRepo");
        GitHubRepositoryDTO gitHubRepositoryDTO = new GitHubRepositoryDTO(1L, "nowaNazwa", "nowyOpis", "nowyUrl", 13, LocalDateTime.of(2024, 1, 1, 23, 59), "nowyAutor", "nowaNazwaRepo");
        Mockito.when(gitHubRepositoryService.updateLocalGitHubRepository("autor", "nazwaRepo", updateGitHubRepository)).thenReturn(gitHubRepositoryDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/local/repositories/{owner}/{repositoryName}", "autor", "nazwaRepo")
                        .content(objectMapper.writeValueAsString(updateGitHubRepository))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("nowaNazwa"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("nowyOpis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloneUrl").value("nowyUrl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars").value(13))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").value("2024-01-01T23:59:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value("nowyAutor"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.repositoryName").value("nowaNazwaRepo"));
    }

    @Test
    void deleteLocalGitHubRepository_successfulDelete_repoDeleted() throws Exception {
        new GitHubRepositoryDTO(1L, "nazwa", "opis", "url", 0, LocalDateTime.of(2025, 1, 1, 23, 59), "autor", "nazwaRepo");

        mockMvc.perform(MockMvcRequestBuilders.delete("/local/repositories/{owner}/{repositoryName}", "autor", "nazwaRepo"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(gitHubRepositoryService).deleteLocalGitHubRepository("autor", "nazwaRepo");
    }
}