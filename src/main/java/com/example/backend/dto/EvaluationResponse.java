package com.example.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class EvaluationResponse {
    private int accuracy;
    private String feedback;
    private List<String> goodPoints;
    private List<String> improvementPoints;
    private String detailedAdvice;
} 