package com.example.githubrepository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GitHubRepositoryDTO {
    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private LocalDate createdAt;
}
