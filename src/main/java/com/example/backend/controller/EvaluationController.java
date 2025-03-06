package com.example.backend.controller;

import com.example.backend.dto.EvaluationRequest;
import com.example.backend.dto.EvaluationResponse;
import com.example.backend.service.EvaluationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluate")
public class EvaluationController {
    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public EvaluationResponse evaluateAnswer(@RequestBody EvaluationRequest request) {
        return evaluationService.evaluateAnswer(request);
    }
} 