package com.example.githubrepository.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateGitHubRepository {
    @NotBlank(message = "Please provide a full name")
    private String fullName;
    @NotBlank(message = "Please provide a description")
    private String description;
    @NotBlank(message = "Please provide a url")
    private String cloneUrl;
    @NotNull(message = "Please provide a stars quantity")
    @PositiveOrZero(message = "The value cannot be less than 0")
    private int stars;
    @NotNull(message = "Please provide a creation date")
    @PastOrPresent(message = "The value cannot be in future")
    private LocalDateTime createdAt;
    @NotBlank(message = "Please provide an owner")
    private String owner;
    @NotBlank(message = "Please provide a repository name")
    private String repositoryName;
}
