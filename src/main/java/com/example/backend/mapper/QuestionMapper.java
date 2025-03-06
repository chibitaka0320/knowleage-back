package com.example.backend.mapper;

import com.example.backend.dto.CategoryDto;
import com.example.backend.dto.QuestionDto;
import com.example.backend.entity.Category;
import com.example.backend.entity.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    public QuestionDto toDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setContent(question.getContent());
        dto.setExampleAnswer(question.getExampleAnswer());
        dto.setDetailedContent(question.getDetailedContent());
        dto.setCreatedAt(question.getCreatedAt());
        dto.setUpdatedAt(question.getUpdatedAt());
        
        // カテゴリのマッピング
        if (question.getCategories() != null) {
            List<CategoryDto> categoryDtos = question.getCategories().stream()
                .map(this::toCategoryDto)
                .collect(Collectors.toList());
            dto.setCategories(categoryDtos);
        } else {
            dto.setCategories(new ArrayList<>());
        }
        
        return dto;
    }

    private CategoryDto toCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setCode(category.getCode());
        return dto;
    }

    public Question toEntity(QuestionDto dto) {
        Question question = new Question();
        question.setId(dto.getId());
        question.setContent(dto.getContent());
        question.setExampleAnswer(dto.getExampleAnswer());
        question.setDetailedContent(dto.getDetailedContent());
        question.setCreatedAt(dto.getCreatedAt());
        question.setUpdatedAt(dto.getUpdatedAt());
        return question;
    }
} 