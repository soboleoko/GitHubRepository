package com.example.githubrepository.client;

import com.example.githubrepository.model.GitHubRepositoryInfoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureWireMock(port = 7070)
@AutoConfigureMockMvc
public class GitHubRepositoryClientTests {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private WireMockServer wireMockServer;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    GitHubRepositoryClient gitHubRepositoryClient;

    @BeforeEach
    void setUp() {
        wireMockServer.start();
//        WireMock.configureFor("localhost", 7070);
    }

    @Test
    void getGitHubRepository_SuccessfulGet_RepoReturned() throws Exception {
        //given
        String owner = "owner";
        String repoName = "repoName";
        GitHubRepositoryInfoDTO gitHubRepositoryInfoDTO = new GitHubRepositoryInfoDTO("fullName", "description", "url", 1, LocalDateTime.of(2025, 1, 1, 12, 0, 0));
        stubFor(get(urlEqualTo("/repos/owner/repoName")).willReturn(aResponse()
                .withBody(objectMapper.writeValueAsString(gitHubRepositoryInfoDTO))
                .withHeader("Content-Type", "application/json")));
        //when
        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/owner/repoName"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.full_name").value("fullName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.clone_url").value("url"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stargazers_count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_at").value("2025-01-01T12:00:00"));
        //then
    }

    @Test
    void saveGitHubRepository_successfulPost_repoSaved() throws Exception {
        String owner = "owner";
        String repoName = "repoName";
        GitHubRepositoryInfoDTO gitHubRepositoryInfoDTO = new GitHubRepositoryInfoDTO("fullName", "description", "url", 1, LocalDateTime.of(2025, 1, 1, 12, 0, 0));
        stubFor(get(urlEqualTo("/repos/owner/repoName")).willReturn(aResponse()
                .withBody(objectMapper.writeValueAsString(gitHubRepositoryInfoDTO))
                .withHeader("Content-Type", "application/json")));

        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/owner/repoName"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value("fullName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cloneUrl").value("url"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stars").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").value("2025-01-01T12:00:00"));

    }

    @Test
    void getGitHubRepositoryFromClient_successfulGet_repoReturned() throws JsonProcessingException {
        //given
        String owner = "owner";
        String repoName = "repoName";
        GitHubRepositoryInfoDTO gitHubRepositoryInfoDTO = new GitHubRepositoryInfoDTO("fullName", "description", "url", 1, LocalDateTime.of(2025, 1, 1, 12, 0, 0));
        stubFor(get(urlEqualTo("/repos/owner/repoName")).willReturn(aResponse()
                .withBody(objectMapper.writeValueAsString(gitHubRepositoryInfoDTO))
                .withHeader("Content-Type", "application/json")));
        //when
        GitHubRepositoryInfoDTO result = gitHubRepositoryClient.getGitHubRepository(owner, repoName);
        //then
        Assertions.assertEquals("fullName", result.getFullName());
        Assertions.assertEquals("description", result.getDescription());
        Assertions.assertEquals("url", result.getCloneUrl());
        Assertions.assertEquals(1, result.getStars());
        Assertions.assertEquals(LocalDateTime.of(2025, 1, 1, 12, 0, 0), result.getCreatedAt());

    }
}
