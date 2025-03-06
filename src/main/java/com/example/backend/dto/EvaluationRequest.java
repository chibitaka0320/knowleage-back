package com.example.backend.dto;

import lombok.Data;

@Data
public class EvaluationRequest {
    private String questionContent;
    private String exampleAnswer;
    private String userAnswer;
} 