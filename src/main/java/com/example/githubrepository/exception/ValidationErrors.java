package com.example.githubrepository.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class ValidationErrors {
    private Map<String, List<String>> errorsMap;
}
