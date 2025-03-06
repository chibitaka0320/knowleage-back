package com.example.backend.controller;

import com.example.backend.dto.ApiResponse;
import com.example.backend.entity.Question;
import com.example.backend.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Question>>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<Page<Question>>> getQuestionsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<Long> categoryIds) {
        
        List<Long> finalCategoryIds;
        if (categoryIds != null && !categoryIds.isEmpty()) {
            finalCategoryIds = categoryIds;
        } else if (categoryId != null) {
            finalCategoryIds = List.of(categoryId);
        } else {
            finalCategoryIds = null;
        }
        
        Page<Question> questions = questionService.getQuestions(PageRequest.of(page, size), finalCategoryIds);
        return ResponseEntity.ok(ApiResponse.success(questions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id)
                .map(question -> ResponseEntity.ok(ApiResponse.success(question)))
                .orElse(ResponseEntity.ok(ApiResponse.error("Question not found")));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Question>> createQuestion(@RequestBody Question question) {
        Question createdQuestion = questionService.createQuestion(question);
        return ResponseEntity.ok(ApiResponse.success(createdQuestion, "Question created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Question>> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        return questionService.updateQuestion(id, question)
                .map(updatedQuestion -> ResponseEntity.ok(ApiResponse.success(updatedQuestion, "Question updated successfully")))
                .orElse(ResponseEntity.ok(ApiResponse.error("Question not found")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable Long id) {
        if (questionService.deleteQuestion(id)) {
            return ResponseEntity.ok(ApiResponse.success(null, "Question deleted successfully"));
        }
        return ResponseEntity.ok(ApiResponse.error("Question not found"));
    }
} 