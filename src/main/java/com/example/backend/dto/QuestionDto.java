package com.example.backend.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionDto {
    private Long id;
    private String content;
    private String exampleAnswer;
    private String detailedContent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategoryDto> categories;
} 